package com.example.reservarestaurantes.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.reservarestaurantes.model.Reservas
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class ReservasDao {
    // variables con nombres de colecciones y asignacion de usuario
    private val collection1 = "bookings"
    private val collection2 = "userBookings"
    private val usuario = Firebase.auth.currentUser?.email.toString()

    //asignación de variable de firestore para su posterior uso
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    // inicialización del build de firestore
    init {
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }

    //Obtener cada uno de los datos almacenados en la colección y ser retornados en una lista
    fun getAllData() : MutableLiveData<List<Reservas>> {
        val reservasList = MutableLiveData<List<Reservas>>()
        firestore.collection(collection1).document(usuario).collection(collection2)
            .addSnapshotListener{ instantanea, e ->
                if (e != null) {  //Se valida si se generó algún error en la captura de los documentos
                    return@addSnapshotListener
                }
                if (instantanea != null) {  //Si hay información recuperada...
                    //Recorro la instantanea (documentos) para crear la lista de reservas
                    val lista = ArrayList<Reservas>()
                    instantanea.documents.forEach {
                        val reserva = it.toObject(Reservas::class.java)
                        if (reserva!=null) { lista.add(reserva) }
                    }
                    reservasList.value=lista
                }
            }
        return reservasList
    }

    // Guardar una nueva reserva o si la reserva ya existe. Se editará la misma
    fun saveReserva(reserva: Reservas) {
        val document: DocumentReference
        if (reserva.id.isEmpty()){ // si el id es vacio se va guardar un nuevo dato
            document = firestore.collection(collection1).document(usuario).collection(collection2).document()
            reserva.id = document.id
        } else { // sino se editará
            document = firestore.collection(collection1).document(usuario)
                .collection(collection2).document(reserva.id)
        }
        document.set(reserva)
            .addOnSuccessListener { Log.d("saveReserva","Se creó o modificó una reserva") }
            .addOnCanceledListener { Log.e("saveReserva","NO se creó o modificó una reserva") }
    }

    // Eliminar una reserva
    fun deleteReserva(reserva: Reservas) {
        if (reserva.id.isNotEmpty()) {  //Si el id tiene valor... entonces podemos eliminar la reserva... porque existe...
            firestore.collection(collection1).document(usuario)
                .collection(collection2).document(reserva.id).delete()
                .addOnSuccessListener { Log.d("deleteReserva","Se eliminó una reserva") }
                .addOnCanceledListener { Log.e("deleteReserva","NO se eliminó una reserva") }
        }
    }
}