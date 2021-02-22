package com.example.franchisemanagement.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class StoreModelResponse(
    @SerializedName("message")
    var message: String?, // Store added successfully
    @SerializedName("status")
    var status: Boolean? // true
)
data class StoreModelRequest(
    @SerializedName("address")
    var address: String?, // Kikuyu, NAIROBI
    @SerializedName("franchise_id")
    var franchiseId: Int, // 1
    @SerializedName("name")
    var name: String?, // Noma store
    @SerializedName("phone")
    var phone: String?, // 0989199291
    @SerializedName("store_email")
    var storeEmail: String?, // nstore@gmail.com
    @SerializedName("user_email")
    var userEmail: String? // camilla@gmail.com
)