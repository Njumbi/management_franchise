package com.example.franchisemanagement.ui

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.franchisemanagement.R
import com.example.franchisemanagement.data.ApiClient
import com.example.franchisemanagement.data.model.LoginRequest
import com.example.franchisemanagement.data.model.LoginResponse
import com.example.franchisemanagement.data.model.LoginResponseData
import com.example.franchisemanagement.parseError
import com.example.franchisemanagement.validateEmail
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun createAccountOnClick(view: View) {
        startActivity(Intent(this, RegistrationActivity::class.java))
    }

    fun login(v: View) {
        /* validate if empty */
        if ( TextUtils.isEmpty(et_email.text) && TextUtils.isEmpty(et_password.text) ){
            tl_email.error = "Enter email"
            tl_password.error = "Enter password"
            return
        }

        if (TextUtils.isEmpty(et_email.text)) {
            tl_email.error = "Enter email"
            return
        }

        if (TextUtils.isEmpty(et_password.text)) {
            tl_password.error = "Enter password"
            return
        }

        /* validate email */
        if (!validateEmail(et_email.text.toString())) {
            tl_email.error = "Enter valid email"
            return
        }

        /* progress */
        val pd = ProgressDialog(this)
        pd.setMessage("Processing your request...")
        pd.show()

        /* request */
        val request = LoginRequest(et_email.text.toString(), et_password.text.toString())

        /* make api call */
        val call = ApiClient().service?.login(request)
        call?.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                /* dismiss progress dialog */
                pd.dismiss()

                /* check if request was a successs */
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse?.status == true) {
                        saveUserDetails(loginResponse.data)
                        startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            loginResponse?.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {

                    /* request not success - parse error */
                    Toast.makeText(
                        this@LoginActivity,
                        parseError(response.errorBody()?.string()!!),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

                Log.e("--->", t.localizedMessage)
                pd.dismiss()
                Toast.makeText(this@LoginActivity, "Something went wrong", Toast.LENGTH_SHORT)
                    .show()
            }

        })

    }

    private fun saveUserDetails(data: LoginResponseData) {
        val sharedPref = getSharedPreferences( "FM",Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            putString("SESSION_DATA",  Gson().toJson( data ))
            commit()
        }
    }
}