package com.velagissellint.presentation

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

fun convertDateForUser(dayOfMonth: Int, month: Int, year: Int): String {
    var textDay = "$dayOfMonth."
    if (dayOfMonth < 10)
        textDay = "0$textDay"
    var textMonth = "${month + 1}."
    if (month + 1 < 10)
        textMonth = "0${textMonth}"
    return textDay + textMonth + year
}

fun dateStringPlusInt(dateString: String, day: Int) {
//if (date[0].toString() == "0"){
//
//}

    val formatter = SimpleDateFormat("dd.MM.yyyy")
    val date = formatter.parse(dateString)
    val cal: Calendar = Calendar.getInstance()
    cal.time = date

    Log.d("qweasd",cal.toString())
}
//fun convertStringToDate(dayOfMonth: Int, month: Int, year: Int): String {
//    var textDay = "$dayOfMonth."
//    if (dayOfMonth < 10)
//        textDay = "0$textDay"
//    var textMonth = "${month + 1}."
//    if (month + 1 < 10)
//        textMonth = "0${textMonth}"
//    return textDay+textMonth+year
//}