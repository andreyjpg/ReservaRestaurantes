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
    private val args by navArgs<AddReservasFragmentArgs>()

    private var _binding: FragmentAddReservasBinding? = null
    private val binding get() = _binding!!
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null

    private lateinit var reservasViewModel: ReservasViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        reservasViewModel =
            ViewModelProvider(this)[ReservasViewModel::class.java]
        _binding = FragmentAddReservasBinding.inflate(inflater, container, false)

        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

        binding.btnSaveReserva.setOnClickListener {
            addReserva()
        }

        return binding.root
    }

    private fun addReserva() {
        val name=binding.iptName.text.toString()
        val phone1=parseInt(binding.iptTel1.text.toString())
        val phone2=parseInt(binding.iptTel2.text.toString())
        val reservationTime = binding.iptReservationTime.text.toString()
        val scheduleInit = parseInt(binding.iptSchedule1.text.toString())

        if (name.isNotEmpty()) {
            val reserva= Reservas("", name, phone1, phone2, reservationTime, scheduleInit,
                args.restaurant)
            reservasViewModel.saveReserva(reserva)
            Toast.makeText(requireContext(),getString(R.string.msg_reserva_added), Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.homeFragment)
        } else {  //Mensaje de error...
            Toast.makeText(requireContext(),getString(R.string.msg_reserva_error), Toast.LENGTH_SHORT).show()
        }
    }


}