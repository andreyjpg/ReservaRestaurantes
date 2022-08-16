package com.example.reservarestaurantes.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.reservarestaurantes.R
import com.example.reservarestaurantes.databinding.FragmentAddRestaurantBinding
import com.example.reservarestaurantes.model.Restaurant
import com.example.reservarestaurantes.viewmodel.RestaurantViewModal
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.lang.Integer.parseInt

class AddRestaurantFragment : Fragment() {
    private var _binding: FragmentAddRestaurantBinding? = null
    private val binding get() = _binding!!
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null

    private lateinit var restaurantViewModel: RestaurantViewModal

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        restaurantViewModel =
            ViewModelProvider(this)[RestaurantViewModal::class.java]
        _binding = FragmentAddRestaurantBinding.inflate(inflater, container, false)

        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

        binding.btnSaveRestaurant.setOnClickListener {
            addLugar()
        }

        return binding.root
    }

    private fun addLugar() {
        val name=binding.iptName.text.toString()
        val phone1=parseInt(binding.iptTel1.text.toString())
        val phone2=parseInt(binding.iptTel2.text.toString())
        val prices = binding.iptPriceRange.text.toString()
        val scheduleInit = binding.iptSchedule1.text.toString()
        val scheduleInit = binding.iptSchedule1.text.toString()

        if (nombre.isNotEmpty()) { //Si puedo crear un lugar
            val restaurant= Restaurant("", name, phone1, phone2, prices)
            restaurantViewModel.saveRestaurant(restaurant)
            Toast.makeText(requireContext(),getString(R.string.msg_lugar_added), Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.homeFragment)
        } else {  //Mensaje de error...
            Toast.makeText(requireContext(),getString(R.string.msg_data), Toast.LENGTH_SHORT).show()
        }
    }


}