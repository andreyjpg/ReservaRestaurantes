package com.example.reservarestaurantes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.reservarestaurantes.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // inicialización a Firebase
        FirebaseApp.initializeApp(this)
        auth = Firebase.auth

        // Asignación de los metodos de click listener
        binding.loginBtn.setOnClickListener{
            login()
        }
        binding.registerBtn.setOnClickListener {
            register()
        }
    }

    /*
        metodo que verifica si el usuario esta logueado para pasar a la siguiente pantalla,
        la pantalla del Home
     */
    private fun update(user: FirebaseUser?) {
        if (user != null) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    // Método cuando se inicia la app, asigna el usuario logueado en caso que haya uno
    public override fun onStart() {
        super.onStart()
        val user = auth.currentUser
        update(user)
    }
    /*
        metodo de ingreso de usuario utilizando correo y contraseña
     */
    private fun login() {
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this){
                task ->
            if (task.isSuccessful) { // si usuario es ingresado correctamente se hace la actualizacion de la pantalla
                Log.d("Login user", "Success")
                val user = auth.currentUser
                update(user)
            } else { // en caso de datos incorrectos no se muestra la siguiente pantalla y solo se muestra un error
                Log.d("Login user", "Failed")
                Toast.makeText(baseContext, "Failded!", Toast.LENGTH_LONG).show()
                update(null)
            }
        }
    }

    /*
        Registro de usuario utilizando correo y contraseña
     */
    private fun register() {
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this){
                task ->
            if (task.isSuccessful) {
                Log.d("Creating user", "Registration")
                val user = auth.currentUser
                update(user)
            } else {
                Log.d("Creating user", "Failed")
                Toast.makeText(baseContext, "Fallido!", Toast.LENGTH_LONG).show()
                update(null)
            }
        }


    }
}