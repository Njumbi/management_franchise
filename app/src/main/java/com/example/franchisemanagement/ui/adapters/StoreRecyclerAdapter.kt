package com.example.franchisemanagement.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.franchisemanagement.R
import com.example.franchisemanagement.data.model.StoreModel
import com.example.franchisemanagement.formatDate
import kotlinx.android.synthetic.main.store_item.view.*
import java.text.SimpleDateFormat

class StoreRecyclerAdapter() :
    RecyclerView.Adapter<StoreRecyclerAdapter.StoreViewModel>() {
    var storedata = mutableListOf<StoreModel.Data>()

    fun setData(data: List<StoreModel.Data>) {
        storedata.clear()
        storedata.addAll(data)

        notifyDataSetChanged()

    }

    class StoreViewModel(view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewModel {
        return StoreViewModel(
            LayoutInflater.from(parent.context).inflate(R.layout.store_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: StoreViewModel, position: Int) {
        var data = storedata[position]

        holder.itemView.storeTitle.text = data.name
        holder.itemView.franchiseName.text = data.franchise?.name
        holder.itemView.storeEmail.text = data.email
        holder.itemView.storeNumber.text = data.phone
        holder.itemView.storeLocation.text = data.address
        holder.itemView.storeDate.text = data.createdAt?.let {(formatDate(it))  }


    }

    override fun getItemCount(): Int {
        return storedata.size
    }
}