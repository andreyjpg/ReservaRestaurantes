package com.example.reservarestaurantes.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reservarestaurantes.R
import com.example.reservarestaurantes.adapter.RestaurantAdapter
import com.example.reservarestaurantes.databinding.FragmentRestaurantBinding
import com.example.reservarestaurantes.viewmodel.RestaurantViewModal

class RestaurantFragment : Fragment() {
    private var _binding: FragmentRestaurantBinding? = null
    private val binding get() = _binding!!
    private lateinit var restaurantViewModel: RestaurantViewModal

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        restaurantViewModel =
            ViewModelProvider(this).get(RestaurantViewModal::class.java)
        _binding = FragmentRestaurantBinding.inflate(inflater, container, false)
        binding.addRestaurant.setOnClickListener {
            findNavController().navigate(R.id.action_nav_restaurant_to_addRestaurantFragment)
        }

        //Activar el reciclador...
        val restaurantAdapter=RestaurantAdapter()
        val reciclador = binding.reciclador
        reciclador.adapter = restaurantAdapter
        reciclador.layoutManager = LinearLayoutManager(requireContext())
        restaurantViewModel.getAllData.observe(viewLifecycleOwner) {
            restaurantAdapter.setData(it)
        }


        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}