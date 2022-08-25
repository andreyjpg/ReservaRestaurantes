package com.example.reservarestaurantes.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.reservarestaurantes.model.Restaurant
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class RestaurantDao {
    // inicialización de variables
    private val collection = "restaurants"
    private val usuario = Firebase.auth.currentUser?.email.toString()
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    //instanciar configuración firestore
    init {
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }

    // obtener una lista de restaurantes ingresados
    fun getAllData() : MutableLiveData<List<Restaurant>> {
        val restaurantsList = MutableLiveData<List<Restaurant>>()
        firestore.collection(collection)
            .addSnapshotListener{ instantanea, e ->
                if (e != null) {  //Se valida si se generó algún error en la captura de los documentos
                    return@addSnapshotListener
                }
                if (instantanea != null) {  //Si hay información recuperada...
                    //Recorro la instantanea (documentos) para crear la lista de restaurantes
                    val lista = ArrayList<Restaurant>()
                    instantanea.documents.forEach {
                        val restaurant = it.toObject(Restaurant::class.java)
                        if (restaurant!=null) { lista.add(restaurant) }
                    }
                    restaurantsList.value=lista
                }
            }
        return restaurantsList
    }

    //Guardado del restaurante o edicion del mismo
    fun saveRestaurant(restaurant: Restaurant) {
        val document: DocumentReference
        if (restaurant.id.isEmpty()){ // si el id esta vacio quiere decir que se debe crear uno nuevo
            document = firestore.collection(collection).document()
            restaurant.id = document.id
        } else {// sino se edita el documento con el id que se envio
            document = firestore.collection(collection).document(restaurant.id)
        }
        document.set(restaurant)
            .addOnSuccessListener { Log.d("saveRestaurant","Se creó o modificó un restaurante") }
            .addOnCanceledListener { Log.e("saveRestaurant","NO se creó o modificó un restaurante") }
    }

    // metodo para eliminar el restaurante utilizando el id que se envia
    fun deleteRestaurant(restaurant: Restaurant) {
        if (restaurant.id.isNotEmpty()) {  //Si el id tiene valor... entonces podemos eliminar el restaurant... porque existe...
            firestore.collection(collection).document(restaurant.id).delete()
                .addOnSuccessListener { Log.d("deleteRestaurant","Se eliminó un restaurante") }
                .addOnCanceledListener { Log.e("deleteRestaurant","NO se eliminó un restaurante") }
        }
    }
}