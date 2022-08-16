package com.example.reservarestaurantes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.reservarestaurantes.data.RestaurantDao
import com.example.reservarestaurantes.model.Restaurant
import com.example.reservarestaurantes.repository.RestaurantRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RestaurantViewModal(application: Application): AndroidViewModel(application) {
    //Atributo para acceder al repositorio de Lugar
    private val repository: RestaurantRepository = RestaurantRepository(RestaurantDao())

    fun saveRestaurant(restaurant: Restaurant) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveRestaurant(restaurant)
        }
    }



}