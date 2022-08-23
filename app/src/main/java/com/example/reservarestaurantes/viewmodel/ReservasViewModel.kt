package com.example.reservarestaurantes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.reservarestaurantes.data.ReservasDao
import com.example.reservarestaurantes.data.RestaurantDao
import com.example.reservarestaurantes.model.Reservas
import com.example.reservarestaurantes.model.Restaurant
import com.example.reservarestaurantes.repository.ReservasRepository
import com.example.reservarestaurantes.repository.RestaurantRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReservasViewModel(application: Application): AndroidViewModel(application) {
    //Atributo para acceder al repositorio de Reservas
    private val repository: ReservasRepository = ReservasRepository(ReservasDao())

    val getAllData: MutableLiveData<List<Reservas>> = repository.getAllData

    fun deleteReserva(reserva: Reservas) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteReserva(reserva)
        }
    }

    fun saveReserva(reserva: Reservas) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveReserva(reserva)
        }
    }



}