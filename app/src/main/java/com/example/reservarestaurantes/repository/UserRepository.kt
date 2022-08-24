package com.example.reservarestaurantes.repository

import androidx.lifecycle.MutableLiveData
import com.example.reservarestaurantes.data.RestaurantDao
import com.example.reservarestaurantes.data.UserDao
import com.example.reservarestaurantes.model.Restaurant
import com.example.reservarestaurantes.model.User

//Repositorio de usuario que traerá la funcionalidad de consulta que está en DAO.
class UserRepository(private val userDao: UserDao) {
    fun saveUser(user: User) {
        userDao.saveUser(user)
    }

}