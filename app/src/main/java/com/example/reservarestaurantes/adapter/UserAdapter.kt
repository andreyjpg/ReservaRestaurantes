package com.example.reservarestaurantes.adapter

import com.example.reservarestaurantes.databinding.FragmentUpdateRestaurantBinding
import com.example.reservarestaurantes.databinding.FragmentUserBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class UserAdapter{
    private val usuario = Firebase.auth.currentUser?.email.toString()
    private var _binding: FragmentUserBinding? = null

}
