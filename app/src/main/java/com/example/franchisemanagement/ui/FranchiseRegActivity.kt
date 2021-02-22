package com.example.franchisemanagement.ui

import android.app.ProgressDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.franchisemanagement.R
import com.example.franchisemanagement.data.ApiClient
import com.example.franchisemanagement.data.model.*
import com.example.franchisemanagement.parseError
import com.example.franchisemanagement.validateEmail
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_franchise_reg.*
import kotlinx.android.synthetic.main.activity_franchise_reg.ly_address
import kotlinx.android.synthetic.main.activity_franchise_reg.ly_email
import kotlinx.android.synthetic.main.activity_franchise_reg.ly_name
import kotlinx.android.synthetic.main.activity_franchise_reg.tx_address
import kotlinx.android.synthetic.main.activity_franchise_reg.tx_email
import kotlinx.android.synthetic.main.activity_franchise_reg.tx_name
import kotlinx.android.synthetic.main.activity_franchise_reg.tx_phone
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.activity_store_reg.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FranchiseRegActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_franchise_reg)
    }

    fun fSubmit(view: View) {
        if (TextUtils.isEmpty(tx_name.text)) {
            ly_name.error = "Enter franchise name"
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
        if (TextUtils.isEmpty(tx_email.text)) {
            ly_email.error = "Enter your email"
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
        val sharedPref = getSharedPreferences("FM",Context.MODE_PRIVATE)
        val sessionData = sharedPref.getString("SESSION_DATA", null)

        val loginResponseData = Gson().fromJson(sessionData, LoginResponseData::class.java)

        // send request
        val request = AddFranchiseRequest(
            email = loginResponseData.email,
            name = tx_name.text.toString(),
            franchise_email = tx_email.text.toString(),
            phone = tx_phone.text.toString(),
            address = tx_address.text.toString()
        )

        val call = ApiClient().service?.franchise(request)
        call?.enqueue(object : Callback<AddFranchiseResponse> {
            override fun onResponse(
                call: Call<AddFranchiseResponse>,
                response: Response<AddFranchiseResponse>
            ) {
                //pd dismiss
                pd.dismiss()

                //check if successful
                if (response.isSuccessful) {
                    val addFranchiseResponse = response.body()
                    if (addFranchiseResponse?.status == true) {
                        finish()
                    } else {
                        Toast.makeText(
                            this@FranchiseRegActivity,
                            addFranchiseResponse?.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    //if not successful
                    Toast.makeText(
                        this@FranchiseRegActivity,
                        parseError(response?.errorBody().toString()),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<AddFranchiseResponse>, t: Throwable) {
                Log.e("--->", t.localizedMessage)
                pd.dismiss()
                Toast.makeText(
                    this@FranchiseRegActivity,
                    "Something went wrong",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

        })


    }
}