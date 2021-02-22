package com.example.franchisemanagement.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.franchisemanagement.R
import com.example.franchisemanagement.data.ApiClient
import com.example.franchisemanagement.data.ApiService
import com.example.franchisemanagement.data.model.EmailModel
import com.example.franchisemanagement.data.model.FranchiseModel
import com.example.franchisemanagement.data.model.StoreModel
import com.example.franchisemanagement.getLoggedInUser
import com.example.franchisemanagement.parseError
import com.example.franchisemanagement.ui.StoreRegActivity
import com.example.franchisemanagement.ui.adapters.FranchiseRecyclerAdapter
import com.example.franchisemanagement.ui.adapters.StoreRecyclerAdapter
import kotlinx.android.synthetic.main.franchise_fragment.*
import kotlinx.android.synthetic.main.store_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreFragment : Fragment() {
    private var adapter: StoreRecyclerAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.store_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        storeAdd.setOnClickListener {
            val intent = Intent(activity, StoreRegActivity::class.java)
            startActivity(intent)
        }
        /* recylerview */
        adapter = StoreRecyclerAdapter()
        rvStore.layoutManager = LinearLayoutManager(requireContext())
        rvStore.adapter = adapter

        fetchStores()
    }

    //fetch all stores

    fun fetchStores() {
        //make request
        var emailRequest = EmailModel(email = getLoggedInUser(requireActivity()).email)

        //make a call to the api
        var call = ApiClient()?.service?.fetchAllStores(emailRequest)
        call?.enqueue(object : Callback<StoreModel> {
            override fun onResponse(call: Call<StoreModel>, response: Response<StoreModel>) {
                progress.visibility = View.GONE
                if (response.isSuccessful) {
                    setDataToRv(response.body()?.data)

                } else {
                    Toast.makeText(
                        requireActivity(),
                        parseError(response.errorBody().toString()),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

            override fun onFailure(call: Call<StoreModel>, t: Throwable) {
                Toast.makeText(requireActivity(), t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onResume() {
        super.onResume()
        fetchStores()
    }

    private fun setDataToRv(data: List<StoreModel.Data?>?) {
        /* create adapter */
        adapter?.setData(data = data as List<StoreModel.Data>)
    }

}