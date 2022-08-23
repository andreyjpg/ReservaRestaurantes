package com.example.reservarestaurantes.ui.home

import android.app.AlertDialog
import android.app.ProgressDialog
import android.net.Uri
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
import com.example.reservarestaurantes.databinding.FragmentUpdateReservasBinding
import com.example.reservarestaurantes.model.Reservas
import com.example.reservarestaurantes.viewmodel.ReservasViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.lang.Integer.parseInt

class UpdateBookingFragment : Fragment() {
    private val args by navArgs<UpdateBookingFragmentArgs>()

    private var _binding: FragmentUpdateReservasBinding? = null
    private val binding get() = _binding!!

    private lateinit var reservasViewModel: ReservasViewModel

    private var progressDialog: ProgressDialog? = null
    //usuario
    private val usuario= Firebase.auth.currentUser?.email.toString()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        reservasViewModel =
            ViewModelProvider(this)[reservasViewModel::class.java]
        _binding = FragmentUpdateReservasBinding.inflate(inflater, container, false)

        //asignacion de los valores del args a los campos de texto
        binding.iptName.setText(args.booking.name)
        binding.iptTel1.setText(args.booking.phone1)
        binding.iptTel2.setText(args.booking.phone2.toString())
        binding.iptReservationTime.setText(args.booking.reservationTime)
        binding.iptSchedule1.setText(args.booking.bookingTime)

        //click listener con las acciones de eliminar, editar, seleccion de imagenes y carga
        binding.btnUpdateReserva.setOnClickListener { updateNft() }
        binding.btnDeleteReserva.setOnClickListener { deleteNft() }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun deleteNft() {
        val pantalla= AlertDialog.Builder(requireContext())

        pantalla.setTitle(R.string.delete)
        pantalla.setMessage(getString(R.string.seguroBorrar)+" ${args.booking.name}?")

        pantalla.setPositiveButton(getString(R.string.si)) { _,_ ->
            reservasViewModel.deleteReserva(args.booking)
            findNavController().navigate(R.id.action_updateBookingFragment_to_bookingFragment)
        }

        pantalla.setNegativeButton(getString(R.string.no)) { _,_ -> }
        pantalla.create().show()
    }

    private fun updateNft() {
        val name=binding.iptName.text.toString()
        val phone1=parseInt(binding.iptTel1.text.toString())
        val phone2=parseInt(binding.iptTel2.text.toString())
        val schedule=binding.iptSchedule1.text.toString()
        val reservation= parseInt(binding.iptReservationTime.text.toString())

        if (name.isNotEmpty()) { //se actualiza mientras nombre este con datos
            val reserva= Reservas(args.booking.id,name,phone1,phone2,schedule, reservation, args.booking.restaurant)
            reservasViewModel.saveReserva(reserva)
            Toast.makeText(requireContext(),getString(R.string.msg_reserva_update), Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateBookingFragment_to_bookingFragment)
        } else {  //Mensaje de error...
            Toast.makeText(requireContext(),getString(R.string.msg_err), Toast.LENGTH_SHORT).show()
        }
    }

}