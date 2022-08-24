package com.example.reservarestaurantes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.reservarestaurantes.data.RestaurantDao
import com.example.reservarestaurantes.data.UserDao
import com.example.reservarestaurantes.model.Restaurant
import com.example.reservarestaurantes.model.User
import com.example.reservarestaurantes.repository.RestaurantRepository
import com.example.reservarestaurantes.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application): AndroidViewModel(application) {
    private val repository: UserRepository = UserRepository(UserDao())
// Hace el llamado al repositorio y ejecuta saveUser que a su vez se llama en el DAO.
    fun saveUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveUser(user)
        }
    }



}