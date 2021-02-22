package com.example.franchisemanagement.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.franchisemanagement.R
import com.example.franchisemanagement.data.model.LoginResponseData
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Response.error

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchData()
    }
    private fun fetchData(){
        /* get data from shared prefs */
        val sharedPref = requireActivity().getSharedPreferences("FM",Context.MODE_PRIVATE)
        val sessionData = sharedPref.getString("SESSION_DATA", null)

        val loginResponseData = Gson().fromJson(sessionData, LoginResponseData::class.java)

        /* set to views */
        loginResponseData?.Franchises?.let {
            tv_franchises.text = it.size.toString()
        }
        loginResponseData?.Stores?.let {
            tv_stores.text = it.size.toString()
        }
    }


    override fun onResume() {
        super.onResume()
        fetchData()
    }
}
