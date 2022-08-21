package com.example.reservarestaurantes.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.sql.Time

@Parcelize
data class Restaurant (
    var id: String,
    var name: String,
    var phone1: Int,
    var phone2: Int?,
    var priceRange: String,
    var scheduleInit: Int,
    var scheduleEnd: Int,
    var reservationNumber: Int,
    var Address: String,
    var userCreate: String,
    var googleAddress: String,
) : Parcelable {
    constructor() :
            this("", "", 0, null, "", 0, 0, 0, "", "","")
}