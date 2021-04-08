package com.example.dollarchecker.utility

import java.text.SimpleDateFormat
import java.util.*

val utils = SimpleDateFormat("dd/MM/yyyy")

fun Calendar.getAsText():String{
    return utils.format(this.time)
}

fun Calendar.changeMonth(count: Int){
    this.add(Calendar.MONTH, count)
}