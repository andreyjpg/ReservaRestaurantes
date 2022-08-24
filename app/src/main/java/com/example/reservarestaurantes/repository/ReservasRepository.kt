package com.example.reservarestaurantes.repository

import androidx.lifecycle.MutableLiveData
import com.example.reservarestaurantes.data.ReservasDao
import com.example.reservarestaurantes.model.Reservas

// Repositorio para acceder al Dao
class ReservasRepository(private val reservasDao: ReservasDao) {
    fun saveReserva(reserva: Reservas) {
        reservasDao.saveReserva(reserva)
    }

    fun deleteReserva(reserva: Reservas) {
        reservasDao.deleteReserva(reserva)
    }

    val getAllData : MutableLiveData<List<Reservas>> = reservasDao.getAllData()
}