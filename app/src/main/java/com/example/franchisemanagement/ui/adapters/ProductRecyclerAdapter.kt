package com.example.franchisemanagement.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.franchisemanagement.R
import com.example.franchisemanagement.data.model.FetchProducts
import com.example.franchisemanagement.formatDate
import kotlinx.android.synthetic.main.product_item.view.*
import java.text.SimpleDateFormat

class ProductRecyclerAdapter() :
    RecyclerView.Adapter<ProductRecyclerAdapter.ProductViewModel>() {
    var dataList = mutableListOf<FetchProducts.Data>()

    fun setData(data: List<FetchProducts.Data>) {
        dataList.clear()
        dataList.addAll(data)

        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewModel {
        return ProductViewModel(
            LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProductViewModel, position: Int) {
        val data = dataList[position]

        holder.itemView.productName.text = data.name
        holder.itemView.code.text = data.productCode
        holder.itemView.productCategory.text = data.category
        holder.itemView.productPrice.text = data.price.toString()
        holder.itemView.date.text = data.createdAt?.let { formatDate(it) }
        holder.itemView.productQuantity.text = data.quantity

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class ProductViewModel(view: View) : RecyclerView.ViewHolder(view) {

    }
}