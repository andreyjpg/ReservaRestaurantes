package com.example.reservarestaurantes.repository

import androidx.lifecycle.MutableLiveData
import com.example.reservarestaurantes.data.RestaurantDao
import com.example.reservarestaurantes.model.Restaurant

class RestaurantRepository(private val restaurantDao: RestaurantDao) {
    // repositorio usado para el acceso al dao
    fun saveRestaurant(restaurant: Restaurant) {
        restaurantDao.saveRestaurant(restaurant)
    }

    fun deleteRestaurant(restaurant: Restaurant) {
        restaurantDao.deleteRestaurant(restaurant)
    }

    val getAllData : MutableLiveData<List<Restaurant>> = restaurantDao.getAllData()
}