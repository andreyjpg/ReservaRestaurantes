package com.example.reservarestaurantes.repository

import com.example.reservarestaurantes.data.RestaurantDao
import com.example.reservarestaurantes.model.Restaurant

class RestaurantRepository(private val restaurantDao: RestaurantDao) {
    fun saveRestaurant(restaurant: Restaurant) {
        restaurantDao.saveRestaurant(restaurant)
    }

}