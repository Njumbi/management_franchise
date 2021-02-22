package com.example.franchisemanagement.data.model


import com.google.gson.annotations.SerializedName

data class FranchiseModel(
    @SerializedName("data")
    var data: List<Data?>?,
    @SerializedName("message")
    var message: String?, // Fetched all franchises
    @SerializedName("status")
    var status: Boolean? // true
) {
    data class Data(
        @SerializedName("address")
        var address: String?, // Nairobi
        @SerializedName("createdAt")
        var createdAt: String?, // 2021-01-19T12:48:52.000Z
        @SerializedName("email")
        var email: String?, // camilla@gmail.com
        @SerializedName("franchise_code")
        var franchiseCode: String?, // 6986E7AC
        @SerializedName("id")
        var id: Int?, // 3
        @SerializedName("name")
        var name: String?, // Camilla Franchises
        @SerializedName("owner_id")
        var ownerId: Int?, // 6
        @SerializedName("phone")
        var phone: String?, // 07219427545
        @SerializedName("updatedAt")
        var updatedAt: String? // 2021-01-19T12:48:52.000Z
    )
}