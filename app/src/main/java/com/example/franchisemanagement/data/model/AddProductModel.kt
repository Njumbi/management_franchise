package com.example.franchisemanagement.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ProductResponse(
    @SerializedName("message")
    var message: String?, // Product successfully saved
    @SerializedName("status")
    var status: Boolean? // true
)

data class ProductModel(
    @SerializedName("category")
    var category: String?, // beverage
    @SerializedName("description")
    var description: String?, // Just awesome soda
    @SerializedName("name")
    var name: String?, // cocacola
    @SerializedName("price")
    var price: Int?, // 50
    @SerializedName("quantity")
    var quantity: String?, // 500 mL
    @SerializedName("stock")
    var stock: Int?, // 20
    @SerializedName("store_id")
    var storeId: Int?, // 2
    @SerializedName("user_email")
    var userEmail: String? // camilla@gmail.com
)
