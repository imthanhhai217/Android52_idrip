package com.vndevpro.android52_idrip.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vndevpro.android52_idrip.R
import com.vndevpro.android52_idrip.databinding.LayoutProductItemBinding
import com.vndevpro.android52_idrip.models.Product

class ListProductAdapter : RecyclerView.Adapter<ListProductAdapter.ProductViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun updateData(data: List<Product>) {
        differ.submitList(data)
    }

    class ProductViewHolder(private val binding: LayoutProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(product: Product) {
            binding.product = product
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_product_item, parent, false)
        val binding = LayoutProductItemBinding.bind(view)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bindData(differ.currentList[position])
    }

    override fun getItemCount() = differ.currentList.size
}