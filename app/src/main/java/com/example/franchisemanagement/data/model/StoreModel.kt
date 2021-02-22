package com.example.franchisemanagement.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class StoreModel(
    @SerializedName("data")
    var `data`: List<Data?>?,
    @SerializedName("message")
    var message: String?, // Success
    @SerializedName("status")
    var status: Boolean? // true
) {
    @Keep
    data class Data(
        @SerializedName("acc_officer")
        var accOfficer: Any?, // null
        @SerializedName("address")
        var address: String?, // Kikuyu, NAIROBI
        @SerializedName("createdAt")
        var createdAt: String?, // 2021-01-12T05:20:16.000Z
        @SerializedName("email")
        var email: String?, // nstore@gmail.com
        @SerializedName("Franchise")
        var franchise: Franchise?,
        @SerializedName("franchise_id")
        var franchiseId: Int?, // 1
        @SerializedName("id")
        var id: Int?, // 1
        @SerializedName("name")
        var name: String?, // Ngethe's store
        @SerializedName("phone")
        var phone: String?, // 0989199291
        @SerializedName("sales_person")
        var salesPerson: Any?, // null
        @SerializedName("store_owner_id")
        var storeOwnerId: Int?, // 1
        @SerializedName("updatedAt")
        var updatedAt: String? // 2021-01-12T05:20:16.000Z
    ) {
        @Keep
        data class Franchise(
            @SerializedName("address")
            var address: String?, // Nairobi
            @SerializedName("createdAt")
            var createdAt: String?, // 2021-01-12T05:19:37.000Z
            @SerializedName("email")
            var email: String?, // ngethe_fran@gmail.com
            @SerializedName("franchise_code")
            var franchiseCode: String?, // 881011BE
            @SerializedName("id")
            var id: Int?, // 1
            @SerializedName("name")
            var name: String?, // Ngethes Franchises
            @SerializedName("owner_id")
            var ownerId: Int?, // 1
            @SerializedName("phone")
            var phone: String?, // 07219427545
            @SerializedName("updatedAt")
            var updatedAt: String? // 2021-01-12T05:19:37.000Z
        )
    }
}