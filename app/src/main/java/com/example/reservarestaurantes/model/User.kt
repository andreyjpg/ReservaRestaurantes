package com.example.reservarestaurantes.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.sql.Time

//Modelo de usuario
@Parcelize
data class User (
    var id: String,
    var name: String,
    var lastname: String,
    var email: String

) : Parcelable {
    constructor() :
            this("", "","","")
}