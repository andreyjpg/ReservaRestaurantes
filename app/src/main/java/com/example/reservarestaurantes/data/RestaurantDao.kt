package com.example.reservarestaurantes.data

import android.util.Log
import com.example.reservarestaurantes.model.Restaurant
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class RestaurantDao {
    private val collection1 = "restaurantReservationApp"
    private val collection2 = "restaurants"
    private val usuario = Firebase.auth.currentUser?.email.toString()
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    init {
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }

    fun saveRestaurant(restaurant: Restaurant) {
        val document: DocumentReference
        if (restaurant.id.isEmpty()){
            document = firestore.collection(collection1).document().collection(collection2).document()
            restaurant.id = document.id
        } else {
            document = firestore.collection(collection1).document()
                .collection(collection2).document(restaurant.id)
        }
        document.set(restaurant)
            .addOnSuccessListener { Log.d("saveRestaurant","Se creó o modificó un restaurante") }
            .addOnCanceledListener { Log.e("saveRestaurant","NO se creó o modificó un restaurante") }
    }
}