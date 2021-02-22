package com.example.franchisemanagement.data.model

data class LoginResponse(
    val `data`: LoginResponseData,
    val message: String,
    val status: Boolean
)


data class LoginResponseData(
    val Franchises: List<Franchise?>?,
    val Stores: List<Store?>?,
    val createdAt: String,
    val email: String,
    val id: Int,
    val name: String,
    val password: String,
    val role: Int,
    val updatedAt: String
)

data class Franchise(
    val address: String,
    val createdAt: String,
    val email: String,
    val franchise_code: String,
    val id: Int,
    val name: String,
    val owner_id: Int,
    val phone: String,
    val updatedAt: String
)

data class Store(
    val acc_officer: Any,
    val address: String,
    val createdAt: String,
    val email: String,
    val franchise_id: Int,
    val id: Int,
    val name: String,
    val phone: String,
    val sales_person: Any,
    val store_owner_id: Int,
    val updatedAt: String
)