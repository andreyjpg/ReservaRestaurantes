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
    private val collection = "restaurants"
    private val usuario = Firebase.auth.currentUser?.email.toString()
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    init {
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }

    /* fun getAllData() : MutableLiveData<List<Restaurant>> {
        val restaurantsList = MutableLiveData<List<Restaurant>>()
        firestore.collection(collection1).get().addOnSuccessListener { documents ->
            val lista = ArrayList<Restaurant>()
            for (document in documents) {
                lista.add(document.toObject(Restaurant::class.java));
            }
            restaurantsList.value=lista
        }
        return restaurantsList
    } */

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

    fun saveRestaurant(restaurant: Restaurant) {
        val document: DocumentReference
        if (restaurant.id.isEmpty()){
            document = firestore.collection(collection).document()
            restaurant.id = document.id
        } else {
            document = firestore.collection(collection).document(restaurant.id)
        }
        document.set(restaurant)
            .addOnSuccessListener { Log.d("saveRestaurant","Se creó o modificó un restaurante") }
            .addOnCanceledListener { Log.e("saveRestaurant","NO se creó o modificó un restaurante") }
    }

    fun deleteRestaurant(restaurant: Restaurant) {
        if (restaurant.id.isNotEmpty()) {  //Si el id tiene valor... entonces podemos eliminar el restaurant... porque existe...
            firestore.collection(collection).document(restaurant.id).delete()
                .addOnSuccessListener { Log.d("deleteRestaurant","Se eliminó un restaurante") }
                .addOnCanceledListener { Log.e("deleteRestaurant","NO se eliminó un restaurante") }
        }
    }
}