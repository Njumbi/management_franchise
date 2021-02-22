package com.example.franchisemanagement.ui.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.franchisemanagement.R
import com.example.franchisemanagement.data.model.FranchiseModel
import com.example.franchisemanagement.formatDate
import kotlinx.android.synthetic.main.franchise_item.view.*
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZonedDateTime
import java.util.*


class FranchiseRecyclerAdapter() :
    RecyclerView.Adapter<FranchiseRecyclerAdapter.FranchiseViewModel>() {
    private var dataList = mutableListOf<FranchiseModel.Data>()

    fun setData(data: List<FranchiseModel.Data>) {
        dataList.clear()
        dataList.addAll(data)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FranchiseViewModel {
        return FranchiseViewModel(
            LayoutInflater.from(parent.context).inflate(R.layout.franchise_item, parent, false)
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: FranchiseViewModel, position: Int) {
        var data = dataList[position]

        holder.itemView.franchiseTitle.text = data.name
        holder.itemView.franchiseLocation.text = data.address
        holder.itemView.franchiseEmail.text = data.email
        holder.itemView.franchiseDate.text = data.createdAt?.let { formatDate(it) }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }


    class FranchiseViewModel(view: View) : RecyclerView.ViewHolder(view) {

    }
}
