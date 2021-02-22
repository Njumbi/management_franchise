package com.example.franchisemanagement.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class FetchProducts(
    @SerializedName("data")
    var `data`: List<Data?>?,
    @SerializedName("message")
    var message: String?, // Success
    @SerializedName("status")
    var status: Boolean? // true
) {
    @Keep
    data class Data(
        @SerializedName("category")
        var category: String?, // beverage
        @SerializedName("createdAt")
        var createdAt: String?, // 2021-01-25T12:46:45.000Z
        @SerializedName("description")
        var description: String?, // Just awesome soda
        @SerializedName("id")
        var id: Int?, // 2
        @SerializedName("name")
        var name: String?, // cocacola
        @SerializedName("price")
        var price: Int?, // 50
        @SerializedName("product_code")
        var productCode: String?, // 8486644085
        @SerializedName("product_owner_id")
        var productOwnerId: Int?, // 6
        @SerializedName("ProductStocks")
        var productStocks: List<ProductStock?>?,
        @SerializedName("quantity")
        var quantity: String?, // 500 mL
        @SerializedName("stock")
        var stock: Int?, // 20
        @SerializedName("Store")
        var store: Store?,
        @SerializedName("store_id")
        var storeId: Int?, // 2
        @SerializedName("updatedAt")
        var updatedAt: String?, // 2021-01-25T12:46:45.000Z
        @SerializedName("vat")
        var vat: Any? // null
    ) {
        @Keep
        data class ProductStock(
            @SerializedName("createdAt")
            var createdAt: String?, // 2021-01-25T12:46:45.000Z
            @SerializedName("id")
            var id: Int?, // 2
            @SerializedName("product_id")
            var productId: Int?, // 2
            @SerializedName("quantity")
            var quantity: String?, // 20
            @SerializedName("type")
            var type: Int?, // 0
            @SerializedName("updatedAt")
            var updatedAt: String? // 2021-01-25T12:46:45.000Z
        )

        @Keep
        data class Store(
            @SerializedName("acc_officer")
            var accOfficer: Any?, // null
            @SerializedName("address")
            var address: String?, // Kikuyu, NAIROBI
            @SerializedName("createdAt")
            var createdAt: String?, // 2021-01-20T13:56:10.000Z
            @SerializedName("email")
            var email: String?, // nstore@gmail.com
            @SerializedName("franchise_id")
            var franchiseId: Int?, // 1
            @SerializedName("id")
            var id: Int?, // 2
            @SerializedName("name")
            var name: String?, // Ngethe store
            @SerializedName("phone")
            var phone: String?, // 0989199291
            @SerializedName("sales_person")
            var salesPerson: Any?, // null
            @SerializedName("store_owner_id")
            var storeOwnerId: Int?, // 1
            @SerializedName("updatedAt")
            var updatedAt: String? // 2021-01-20T13:56:10.000Z
        )
    }
}