package com.example.comprepoupe.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.comprepoupe.R

class AdapterProduct(private val context: Context, private val products: MutableList<Product>
):RecyclerView.Adapter<AdapterProduct.ProdutoViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdutoViewHolder {
       val item_list = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false)
        val holder = ProdutoViewHolder(item_list)
        return holder
    }

    override fun onBindViewHolder(holder: ProdutoViewHolder, position: Int) {
        holder.image.setImageResource(products[position].image)
        holder.description.text = products[position].description
        holder.pricePrev.text = products[position].pricePrev
        holder.price.text = products[position].price
    }

    override fun getItemCount(): Int = products.size


    inner class ProdutoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val image = itemView.findViewById<ImageView>(R.id.idImageItem)
        val description = itemView.findViewById<TextView>(R.id.idTextDescription)
        val pricePrev = itemView.findViewById<TextView>(R.id.idPricePrev)
        val price = itemView.findViewById<TextView>(R.id.idPrice)
    }
}