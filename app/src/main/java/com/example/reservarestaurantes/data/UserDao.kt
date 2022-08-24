package com.example.reservarestaurantes.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.reservarestaurantes.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class UserDao {
    private val collection = "users"
    private val usuario = Firebase.auth.currentUser?.email.toString()
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    init {
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }


    //Este método cumple con la función de guardar y actualizar el documento del usuario de acuerdo a si existe o no.
    fun saveUser(user: User) {
        val document: DocumentReference
        if (user.id.isEmpty()){
            document = firestore.collection(collection).document()
            user.id = document.id
        } else {
            document = firestore.collection(collection).document(user.id)
        }
        document.set(user)
            .addOnSuccessListener { Log.d("saveUser","Se creó o modificó un perfil") }
            .addOnCanceledListener { Log.e("saveUser","NO se creó o modificó un perfil") }
    }

}