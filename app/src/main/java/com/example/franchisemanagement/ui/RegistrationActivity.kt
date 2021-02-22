package com.example.franchisemanagement.ui

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.franchisemanagement.R
import com.example.franchisemanagement.data.ApiClient
import com.example.franchisemanagement.data.model.RegistrationRequest
import com.example.franchisemanagement.data.model.RegistrationResponse
import com.example.franchisemanagement.parseError
import com.example.franchisemanagement.validateEmail
import kotlinx.android.synthetic.main.activity_registration.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
    }

    fun register(view: View) {
        //validate email username and password
        if (TextUtils.isEmpty(username.text)) {
            userLayout.error = "Enter username"
            return
        }

        if (TextUtils.isEmpty(registerEmail.text)) {
            emailLayout.error = "Enter Email"
            return
        }
        if (TextUtils.isEmpty(registerPassword.text)) {
            passwordLayout.error = "Enter password"
            return
        }
        // validate email
        if (!validateEmail(registerEmail.text.toString())) {
            emailLayout.error = "Enter valid email"
            return
        }
        //progress
        val pd = ProgressDialog(this)
        pd.setMessage("processing your request")
        pd.show()

        //make a request
        val request = RegistrationRequest(
            email = registerEmail.text.toString(),
            password = registerPassword.text.toString(),
            user_name = username.text.toString()
        )

        //make api call
        val call = ApiClient().service?.register(request)
        call?.enqueue(object : Callback<RegistrationResponse> {
            override fun onResponse(
                call: Call<RegistrationResponse>,
                response: Response<RegistrationResponse>
            ) {
                //dismiss pd
                pd.dismiss()

                /* check if request was a success */
                if (response.isSuccessful) {
                    val registrationResponse = response.body()
                    if (registrationResponse?.status == true) {
                        startActivity(
                            Intent(
                                this@RegistrationActivity,
                                LoginActivity::class.java
                            )
                        )
                        finish()
                    } else {
                        Toast.makeText(
                            this@RegistrationActivity,
                            registrationResponse?.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    //not successful
                    Toast.makeText(
                        this@RegistrationActivity,
                        parseError(response?.errorBody().toString()),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<RegistrationResponse>, t: Throwable) {
                Log.e("--->", t.localizedMessage)
                pd.dismiss()
                Toast.makeText(
                    this@RegistrationActivity,
                    "Something went wrong",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        })
    }
}