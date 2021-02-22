package com.example.franchisemanagement.data

import com.example.franchisemanagement.data.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("register")
    fun register(@Body request: RegistrationRequest): Call<RegistrationResponse>

    @POST("franchise")
    fun franchise(@Body request: AddFranchiseRequest): Call<AddFranchiseResponse>

    @POST("franchise/all")
    fun fetchAllFranchises(@Body request: EmailModel): Call<FranchiseModel>

    @POST("store")
    fun store(@Body request: StoreModelRequest): Call<StoreModelResponse>

    @POST("store/all")
    fun fetchAllStores(@Body request: EmailModel): Call<StoreModel>

    @POST("product")
    fun addProduct(@Body request: ProductModel): Call<ProductModel>

    @POST("product/all")
    fun fetchAllProducts(@Body request: EmailModel):Call<FetchProducts>
}