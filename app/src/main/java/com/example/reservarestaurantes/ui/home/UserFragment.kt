package com.example.reservarestaurantes.ui.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.reservarestaurantes.R
import com.example.reservarestaurantes.databinding.FragmentRestaurantBinding
import com.example.reservarestaurantes.databinding.FragmentUserBinding
import com.example.reservarestaurantes.viewmodel.UserViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


// Este fragment es la parte que se encarga de hacer el visualizado y update del perfil.
class UserFragment : Fragment() {

    // Se manda a llamar al fragment de user.
    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!
    //Se inicializa el usuario logueado actualmente.
    private val usuario = Firebase.auth.currentUser
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    private lateinit var userViewModel: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {userViewModel =
        ViewModelProvider(this).get(UserViewModel::class.java)
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        // Se le asigna el método updateProfile al botón de actualizar perfil que está en el layout del
        // fragment user.
        binding.btnSaveUser.setOnClickListener {
            updateProfile(binding.iptNameUser.text.toString())
        }
        // Se manda a llamar al método updateUI
        updateUI()
        return binding.root
    }

    //Si el usuario existe este método se encarga de actualizar el displayname del usuario logueado.
    private fun updateProfile(name:String){
        val profileUpdates = userProfileChangeRequest {
            displayName = name
        }

        usuario!!.updateProfile(profileUpdates).
        addOnCompleteListener { task ->
            if (task.isSuccessful){
                updateUI()
            }

        }

    }

    // Este método se trata de hacer setText a los campos de NameUser que sería el displayName del usuario y al campo email.
    private fun updateUI(){
        if(usuario != null){
            binding.iptNameUser.setText(usuario.displayName.toString())
            binding.iptEmail.setText(usuario.email.toString())
        }
    }



}