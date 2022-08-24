package com.example.reservarestaurantes.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reservarestaurantes.adapter.ReservasAdapter
import com.example.reservarestaurantes.databinding.FragmentBookingBinding
import com.example.reservarestaurantes.viewmodel.ReservasViewModel
import com.example.reservarestaurantes.viewmodel.RestaurantViewModel

class BookingFragment : Fragment() {
    private lateinit var reservasViewModel: ReservasViewModel
    private var _binding: FragmentBookingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        reservasViewModel =
            ViewModelProvider(this)[ReservasViewModel::class.java]
        // Inflate the layout for this fragment
        _binding = FragmentBookingBinding.inflate(inflater, container, false)

        val reservasAdapter = ReservasAdapter()
        val list = binding.bookingList
        list.adapter = reservasAdapter
        list.layoutManager = LinearLayoutManager(requireContext())
        reservasViewModel = ViewModelProvider(this)[ReservasViewModel::class.java]
        reservasViewModel.getAllData.observe(viewLifecycleOwner) {reservas ->
            reservasAdapter.setData(reservas)
        }

        return binding.root
    }

}