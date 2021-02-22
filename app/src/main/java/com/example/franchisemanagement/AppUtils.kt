package com.example.franchisemanagement

import android.content.Context
import com.example.franchisemanagement.data.model.LoginResponseData
import com.google.gson.Gson
import com.google.gson.JsonObject
import java.text.SimpleDateFormat

const val BASE_URL = "http://159.89.233.116:4000/api/v1/"

private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

fun validateEmail(email: String): Boolean {
    return email.matches(emailPattern.toRegex())
}

fun parseError(errorBoddy: String): String {
    val jobject = Gson().fromJson(errorBoddy, JsonObject::class.java)
    return jobject.get("message").asString
}


fun getLoggedInUser(context: Context): LoginResponseData {
    val sharedPref = context.getSharedPreferences("FM", Context.MODE_PRIVATE)
    val sessionData = sharedPref.getString("SESSION_DATA", null)

    return Gson().fromJson(sessionData, LoginResponseData::class.java)
}

fun formatDate(date: String): String {
    var dateStr = date


    var beforeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    var fdate = beforeFormat.parse(dateStr)

    val afterF = SimpleDateFormat("yyyy-MM-dd HH:mm")
    return afterF.format(fdate)
}