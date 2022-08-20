package com.example.reservarestaurantes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.reservarestaurantes.data.ReservasDao
import com.example.reservarestaurantes.model.Reservas
import com.example.reservarestaurantes.repository.ReservasRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReservasViewModel(application: Application): AndroidViewModel(application) {
    //Atributo para acceder al repositorio de Reservas
    private val repository: ReservasRepository = ReservasRepository(ReservasDao())

    fun saveReserva(reserva: Reservas) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveReserva(reserva)
        }
    }



}