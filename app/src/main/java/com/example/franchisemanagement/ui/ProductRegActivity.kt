package com.example.franchisemanagement.ui

import android.app.ProgressDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.franchisemanagement.R
import com.example.franchisemanagement.data.ApiClient
import com.example.franchisemanagement.data.model.EmailModel
import com.example.franchisemanagement.data.model.LoginResponseData
import com.example.franchisemanagement.data.model.ProductModel
import com.example.franchisemanagement.data.model.StoreModel
import com.example.franchisemanagement.getLoggedInUser
import com.example.franchisemanagement.parseError
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_product_reg.*
import kotlinx.android.synthetic.main.activity_product_reg.ly_menu
import kotlinx.android.synthetic.main.activity_product_reg.ly_name
import kotlinx.android.synthetic.main.activity_product_reg.tx_name
import kotlinx.android.synthetic.main.activity_store_reg.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductRegActivity : AppCompatActivity() {
    val storeResponse = mutableListOf<StoreModel.Data?>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_reg)
        fetchData()
        fetchCategory()

    }

    fun productOnclick(view: View) {

        if (TextUtils.isEmpty(tx_store.text)) {
            ly_menu.error = "Select a store"
            return
        }
        if (TextUtils.isEmpty(tx_category.text)) {
            ly_category.error = "select category"
            return
        }
        if (TextUtils.isEmpty(tx_name.text)) {
            ly_name.error = "Enter product name"
            return
        }
        if (TextUtils.isEmpty(tx_quantity.text)) {
            ly_quantity.error = "Enter product's quantity"
            return
        }
        if (TextUtils.isEmpty(tx_stock.text)) {
            ly_stock.error = "Enter product's stock"
            return
        }
        if (TextUtils.isEmpty(tx_price.text)) {
            ly_price.error = "Enter product's price"
            return
        }
        if (TextUtils.isEmpty(tx_description.text)) {
            ly_description.error = "Enter product's description"
            return
        }
// show pd
        val pd = ProgressDialog(this)
        pd.setMessage("processing your request")
        pd.show()

        // get user email
        val sharedPref = getSharedPreferences("FM", Context.MODE_PRIVATE)
        val sessionData = sharedPref.getString("SESSION_DATA", null)

        val loginResponseData = Gson().fromJson(sessionData, LoginResponseData::class.java)
        //make a request
        val productRequest = ProductModel(
            category = tx_category.text.toString(),
            storeId = getStoreId(),
            name = tx_name.text.toString(),
            quantity = tx_quantity.text.toString(),
            stock = tx_stock.text.toString().toInt(),
            price = tx_price.text.toString().toInt(),
            description = tx_description.text.toString(),
            userEmail = loginResponseData.email

        )

        // make api request
        var call = ApiClient()?.service?.addProduct(productRequest)
        call?.enqueue(object : Callback<ProductModel> {
            override fun onResponse(call: Call<ProductModel>, response: Response<ProductModel>) {
                if (response.isSuccessful) {
                    finish()
                } else {
                    Toast.makeText(
                        this@ProductRegActivity,
                        parseError(response?.errorBody().toString()),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ProductModel>, t: Throwable) {
                Toast.makeText(this@ProductRegActivity, t.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }

    private fun fetchData() {
        var emailRequest = EmailModel(email = getLoggedInUser(this).email)

        var call = ApiClient()?.service?.fetchAllStores(emailRequest)
        call?.enqueue(object : Callback<StoreModel> {
            override fun onResponse(call: Call<StoreModel>, response: Response<StoreModel>) {
                if (response.isSuccessful) {
                    //display stores
                    response.body()?.data?.let { storeResponse.addAll(it) }
                    var storeNames = storeResponse!!.map {
                        return@map it?.name
                    }
                    val adapter =
                        ArrayAdapter(this@ProductRegActivity, R.layout.list_item, storeNames)
                    tx_store.setAdapter(adapter)

                } else {
                    Toast.makeText(
                        this@ProductRegActivity,
                        response.errorBody()?.string(),
                        Toast.LENGTH_SHORT
                    ).show()
                }


            }

            override fun onFailure(call: Call<StoreModel>, t: Throwable) {
                Toast.makeText(this@ProductRegActivity, t.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }

    private fun fetchCategory() {
        val category = listOf(
            "beverage",
            "snacks",
            "grocery",
            "cereals",
            "stationery",
            "flour",
            "cupboard food"
        )
        val adapter = ArrayAdapter(this, R.layout.list_item, category)
        tx_category.setAdapter(adapter)
    }

    private fun getStoreId(): Int {
        var sId = 0
        for (store in storeResponse) {
            if (store?.name == tx_store.text.toString()) {
                sId = store.id!!
                break
            }
        }
        return sId

    }
}
