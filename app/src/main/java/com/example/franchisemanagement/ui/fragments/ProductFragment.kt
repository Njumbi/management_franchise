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
import com.example.franchisemanagement.data.model.EmailModel
import com.example.franchisemanagement.data.model.FetchProducts
import com.example.franchisemanagement.getLoggedInUser
import com.example.franchisemanagement.parseError
import com.example.franchisemanagement.ui.ProductRegActivity
import com.example.franchisemanagement.ui.adapters.ProductRecyclerAdapter
import kotlinx.android.synthetic.main.product_fragment.*
import kotlinx.android.synthetic.main.store_fragment.*
import kotlinx.android.synthetic.main.store_fragment.storeAdd
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductFragment : Fragment() {
    lateinit var adapter: ProductRecyclerAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.product_fragment, container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        storeAdd.setOnClickListener {
            val intent = Intent(activity, ProductRegActivity::class.java)
            startActivity(intent)
        }
        //set up adapter
        adapter = ProductRecyclerAdapter()
        rvProducts.layoutManager = LinearLayoutManager(requireContext())
        rvProducts.adapter = adapter
        //fetch all products
        fetchProducts()
    }

    private fun fetchProducts() {
        //make a request using your email
        val emailRequest = EmailModel(getLoggedInUser(requireContext()).email)

        //make a call to the api
        val call = ApiClient()?.service?.fetchAllProducts(emailRequest)
        call?.enqueue(object : Callback<FetchProducts> {
            override fun onResponse(call: Call<FetchProducts>, response: Response<FetchProducts>) {
                //if success
                progressProduct.visibility = View.GONE
                if (response.isSuccessful) {
                    setDataToRv(response.body()?.data)

                } else {
                    Toast.makeText(
                        requireContext(),
                        parseError(response.errorBody().toString()),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<FetchProducts>, t: Throwable) {
                Toast.makeText(requireContext(), t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }


    override fun onResume() {
        super.onResume()
        fetchProducts()
    }

    private fun setDataToRv(data: List<FetchProducts.Data?>?) {
        /* create adapter */
        adapter?.setData(data = data as List<FetchProducts.Data>)
    }
}