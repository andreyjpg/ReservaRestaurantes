package com.example.reservarestaurantes.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.reservarestaurantes.R
import com.example.reservarestaurantes.databinding.FragmentAddRestaurantBinding
import com.example.reservarestaurantes.model.Restaurant
import com.example.reservarestaurantes.viewmodel.RestaurantViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.lang.Integer.parseInt

class AddRestaurantFragment : Fragment() {
    // creación de variables
    private var _binding: FragmentAddRestaurantBinding? = null
    private val PICK_IMAGE_REQUEST = 71
    private val binding get() = _binding!!
    private val usuario = Firebase.auth.currentUser?.email.toString()
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    private var imageUri: Uri? = null

    private lateinit var restaurantViewModel: RestaurantViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //inicialización del view model
        restaurantViewModel =
            ViewModelProvider(this)[RestaurantViewModel::class.java]
        //inicialización del binding
        _binding = FragmentAddRestaurantBinding.inflate(inflater, container, false)

        // inicialización y creacion de instancias para firebase
        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

        // asignación de los listener en los clicks
        binding.btnSaveRestaurant.setOnClickListener {
            addRestaurant()
        }

        binding.btnChooseImage.setOnClickListener {
            launchGallery()
        }

        binding.btnUploadImage.setOnClickListener {
            uploadImage()
        }

        return binding.root
    }

    // abrir galeria del telefono para seleccionar foto
    private fun launchGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    // asignacion de imagen seleccionada
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        this.imageUri = data?.data
    }

    // guardado de imagenes en firestorage
    private fun uploadImage() {
        val name = binding.iptName.toString()
        val storageReference = FirebaseStorage.getInstance().getReference("imagenes/$usuario/$name")

        this.imageUri?.let {
            storageReference
                .putFile(it)
                .addOnFailureListener {
                    Toast.makeText(requireContext(),getString(R.string.fail_save),Toast.LENGTH_SHORT).show()
                }.addOnSuccessListener { taskSnapshot ->
                    Toast.makeText(requireContext(),getString(R.string.success_save),Toast.LENGTH_SHORT).show()
                }
        }
    }

    /*
        Metodo para agregar el restaurante utilizando los campos de texto
     */
    private fun addRestaurant() {
        val name=binding.iptName.text.toString()
        val phone1=parseInt(binding.iptTel1.text.toString())
        val phone2=parseInt(binding.iptTel2.text.toString())
        val prices = binding.iptPriceRange.text.toString()
        val scheduleInit = parseInt(binding.iptSchedule1.text.toString())
        val scheduleEnd =  parseInt(binding.iptSchedule2.text.toString())
        val reservationTime = parseInt(binding.iptNumberReservations.text.toString())
        val address = binding.iptAddress.text.toString()
        val googleAddress = this.imageUri.toString()

        if (name.isNotEmpty()) {
            val restaurant= Restaurant("", name, phone1, phone2, prices, scheduleInit,
                scheduleEnd, reservationTime, address, usuario,googleAddress)
            restaurantViewModel.saveRestaurant(restaurant)
            Toast.makeText(requireContext(),getString(R.string.msg_restaurant_added), Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addRestaurantFragment_to_nav_restaurant)
        } else {  //Mensaje de error...
            Toast.makeText(requireContext(),getString(R.string.msg_restaurant_error), Toast.LENGTH_SHORT).show()
        }
    }


}