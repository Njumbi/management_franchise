package com.example.franchisemanagement.ui

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.franchisemanagement.R
import com.example.franchisemanagement.data.ApiClient
import com.example.franchisemanagement.data.model.*
import com.example.franchisemanagement.getLoggedInUser
import com.example.franchisemanagement.parseError
import com.example.franchisemanagement.ui.fragments.FranchiseFragment
import com.example.franchisemanagement.ui.fragments.StoreFragment
import com.example.franchisemanagement.validateEmail
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_franchise_reg.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_store_reg.*
import kotlinx.android.synthetic.main.activity_store_reg.ly_address
import kotlinx.android.synthetic.main.activity_store_reg.ly_email
import kotlinx.android.synthetic.main.activity_store_reg.ly_name
import kotlinx.android.synthetic.main.activity_store_reg.tx_address
import kotlinx.android.synthetic.main.activity_store_reg.tx_email
import kotlinx.android.synthetic.main.activity_store_reg.tx_name
import kotlinx.android.synthetic.main.activity_store_reg.tx_phone
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.franchise_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreRegActivity : AppCompatActivity() {

    val franchiseResponse = mutableListOf<FranchiseModel.Data?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_reg)
        fetchdata()

    }

    fun btnSubmit(view: View) {
        if (TextUtils.isEmpty(tx_franchise.text)) {
            ly_menu.error = "Select franchise name"
            return
        }
        if (TextUtils.isEmpty(tx_name.text)) {
            ly_name.error = "Enter your store name"
            return
        }
        if (TextUtils.isEmpty(tx_address.text)) {
            ly_address.error = "Enter you location"
            return
        }
        if (TextUtils.isEmpty(tx_email.text)) {
            ly_email.error = "Enter your email"
            return
        }
        if (TextUtils.isEmpty(tx_phone.text)) {
            ly_email.error = "Enter your phone number"
            return
        }
        /* validate email */
        if (!validateEmail(tx_email.text.toString())) {
            ly_email.error = "Enter valid email"
            return
        }
        /* validate email */
        if (!validateEmail(tx_email.text.toString())) {
            ly_email.error = "Enter valid email"
            return
        }
        //progress
        val pd = ProgressDialog(this)
        pd.setMessage("processing your request")
        pd.show()

        // get user email
        val sharedPref = getSharedPreferences("FM", Context.MODE_PRIVATE)
        val sessionData = sharedPref.getString("SESSION_DATA", null)

        val loginResponseData = Gson().fromJson(sessionData, LoginResponseData::class.java)

        // send request
        val request = StoreModelRequest(
            franchiseId = getFranchiseId(),
            userEmail = loginResponseData.email,
            storeEmail = tx_email.text.toString(),
            name = tx_name.text.toString(),
            phone = tx_phone.text.toString(),
            address = tx_address.text.toString()
        )

        val call = ApiClient().service?.store(request)
        call?.enqueue(object : Callback<StoreModelResponse> {
            override fun onResponse(
                call: Call<StoreModelResponse>,
                response: Response<StoreModelResponse>
            ) {
                //pd dismiss
                pd.dismiss()

                //check if successful
                if (response.isSuccessful) {
                    val addStoreResponse = response.body()
                    if (addStoreResponse?.status == true) {
                      finish()
                    } else {
                        Toast.makeText(
                            this@StoreRegActivity,
                            addStoreResponse?.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    //if not successful
                    Toast.makeText(
                        this@StoreRegActivity,
                        parseError(response?.errorBody().toString()),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<StoreModelResponse>, t: Throwable) {
                Log.e("--->", t.localizedMessage)
                pd.dismiss()
                Toast.makeText(
                    this@StoreRegActivity,
                    "Something went wrong",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

        })


    }

    private fun fetchdata() {
        var emailRequest = EmailModel(email = getLoggedInUser(this).email)
        /* get franchises from api */
        var call = ApiClient().service?.fetchAllFranchises(emailRequest)
        call?.enqueue(object : retrofit2.Callback<FranchiseModel> {
            override fun onResponse(
                call: Call<FranchiseModel>,
                response: Response<FranchiseModel>
            ) {
                if (response.isSuccessful) {
                    response.body()?.data?.let { franchiseResponse.addAll(it) };

                    var fNames = response.body()?.data!!.map {
                        return@map it?.name
                    }

                    val adapter = ArrayAdapter(this@StoreRegActivity, R.layout.list_item, fNames)
                    tx_franchise.setAdapter(adapter)



                } else {
                    Toast.makeText(
                        this@StoreRegActivity,
                        response.errorBody()?.string(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

            override fun onFailure(call: Call<FranchiseModel>, t: Throwable) {
                this@StoreRegActivity
                Toast.makeText(this@StoreRegActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun getFranchiseId(): Int {
        var fId = 0
        for (franchise in franchiseResponse) {
            if (franchise?.name == tx_franchise.text.toString()) {
                fId = franchise.id!!
                break
            }
        }
        return fId
    }

}