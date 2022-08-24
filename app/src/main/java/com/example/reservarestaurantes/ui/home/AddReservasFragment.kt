package com.example.reservarestaurantes.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.reservarestaurantes.R
import com.example.reservarestaurantes.databinding.FragmentAddReservasBinding
import com.example.reservarestaurantes.model.Reservas
import com.example.reservarestaurantes.viewmodel.ReservasViewModel
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.lang.Integer.parseInt

class AddReservasFragment : Fragment() {
    // se obtienen los argumentos enviados, se esta enviando restaurante en este caso
    private val args by navArgs<AddReservasFragmentArgs>()

    //declaracion de variables de firestore y binding
    private var _binding: FragmentAddReservasBinding? = null
    private val binding get() = _binding!!
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null

    // variable de view model
    private lateinit var reservasViewModel: ReservasViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //inicializacion de view Model
        reservasViewModel =
            ViewModelProvider(this)[ReservasViewModel::class.java]
        _binding = FragmentAddReservasBinding.inflate(inflater, container, false)

        //Instanciando firebase
        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

        //listener para el botón de guardar reserva
        binding.btnMainAction.setOnClickListener {
            addReserva()
        }

        return binding.root
    }

    // Metodo de agregar reserva, se obtiene los datos de los inputs de texto
    private fun addReserva() {
        val name=binding.iptName.text.toString()
        val phone1=parseInt(binding.iptTel1.text.toString())
        val phone2=parseInt(binding.iptTel2.text.toString())
        val reservationTime = binding.iptReservationTime.text.toString()
        val scheduleInit = binding.iptSchedule1.text.toString()

        if (name.isNotEmpty()) {
            val reserva= Reservas("", name, phone1, phone2, reservationTime, scheduleInit,
                args.restaurant)
            reservasViewModel.saveReserva(reserva)
            Toast.makeText(requireContext(),getString(R.string.msg_reserva_added), Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addReservasFragment_to_nav_restaurant)
        } else {  //Mensaje de error...
            Toast.makeText(requireContext(),getString(R.string.msg_reserva_error), Toast.LENGTH_SHORT).show()
        }
    }


}