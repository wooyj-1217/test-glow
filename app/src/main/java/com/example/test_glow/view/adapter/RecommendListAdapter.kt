package com.example.test_glow.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.test_glow.databinding.ItemProductBinding
import com.example.test_glow.databinding.ItemRecommendBinding
import com.example.test_glow.network.data.ProductItemData
import com.example.test_glow.network.data.RecommendItemData


class RecommendItemClickListener(val clickListener: (data: RecommendItemData, view: View)-> Unit){
    fun onClick(data : RecommendItemData, view: View) = clickListener(data, view)
}

class RecommendListAdapter(private val clickListener: RecommendItemClickListener)
    : ListAdapter<RecommendItemData, RecommendListAdapter.ViewHolder>(RecommendItemData.DiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position),clickListener)
    }

    class ViewHolder private constructor(private val binding : ItemRecommendBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(item: RecommendItemData, clickListener:RecommendItemClickListener) {
            binding.item = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup) : ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemRecommendBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}