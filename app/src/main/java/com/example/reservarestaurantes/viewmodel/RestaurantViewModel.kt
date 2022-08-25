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

class RestaurantViewModel(application: Application): AndroidViewModel(application) {
    /*
        Atributo para acceder al repositorio de restaurantes, el view model es el que va ser
        accesado desde la pantalla
     */
    private val repository: RestaurantRepository = RestaurantRepository(RestaurantDao())

    // obtener lista y asignación
    val getAllData: MutableLiveData<List<Restaurant>>

    init {  getAllData = repository.getAllData  }

    // Eliminar restaurante
    fun deleteRestaurant(restaurant: Restaurant) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteRestaurant(restaurant)
        }
    }
    // Guardar o editar restaurante
    fun saveRestaurant(restaurant: Restaurant) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveRestaurant(restaurant)
        }
    }



}