package com.example.franchisemanagement.data.model

data class RegistrationRequest(var email:String,var user_name: String, var password: String)

data class RegistrationResponse(var status:Boolean,var message:String)
