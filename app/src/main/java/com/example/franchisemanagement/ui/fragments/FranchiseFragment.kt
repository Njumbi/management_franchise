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
import com.example.franchisemanagement.data.model.FranchiseModel
import com.example.franchisemanagement.getLoggedInUser
import com.example.franchisemanagement.ui.adapters.FranchiseRecyclerAdapter
import com.example.franchisemanagement.ui.FranchiseRegActivity
import kotlinx.android.synthetic.main.franchise_fragment.*

import retrofit2.Call
import retrofit2.Response

class FranchiseFragment : Fragment() {


    private var adapter: FranchiseRecyclerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.franchise_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        franchiseAdd.setOnClickListener {
            val intent = Intent(activity, FranchiseRegActivity::class.java)
            startActivity(intent)
        }

        /* recylerview */
        adapter = FranchiseRecyclerAdapter()

        rvFranchise.layoutManager = LinearLayoutManager(requireContext())
        rvFranchise.adapter = adapter
        fetchdata()
    }

    private fun fetchdata() {
        var emailRequest = EmailModel(email = getLoggedInUser(requireActivity()).email)
        /* get franchises from api */
        var call = ApiClient().service?.fetchAllFranchises(emailRequest)
        call?.enqueue(object : retrofit2.Callback<FranchiseModel> {
            override fun onResponse(
                call: Call<FranchiseModel>,
                response: Response<FranchiseModel>
            ) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    setDataToRv(response.body()?.data)
                } else {
                    Toast.makeText(
                        requireActivity(),
                        response.errorBody()?.string(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

            override fun onFailure(call: Call<FranchiseModel>, t: Throwable) {
                progressBar.visibility = View.GONE
                Toast.makeText(requireActivity(), t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onResume() {
        super.onResume()

        fetchdata()
    }

    private fun setDataToRv(data: List<FranchiseModel.Data?>?) {
        /* create adapter */
        adapter?.setData(data = data as List<FranchiseModel.Data>)
    }


}
