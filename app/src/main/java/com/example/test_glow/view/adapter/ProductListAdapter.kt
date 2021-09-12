package com.example.test_glow.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.test_glow.databinding.ItemProductBinding
import com.example.test_glow.network.data.ProductItemData
import com.example.test_glow.view.fragment.ListFragment
import com.example.test_glow.view.fragment.ListFragmentDirections
import timber.log.Timber

class ProductItemClickListener(val clickListener: (data:ProductItemData, view: View)-> Unit){
    fun onClick(data : ProductItemData, view: View) = clickListener(data, view)
}

class ProductListAdapter(private val clickListener: ProductItemClickListener,  private val fragment: ListFragment)
    :ListAdapter<ProductItemData, ProductListAdapter.ViewHolder>(ProductItemData.DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position),clickListener, position, fragment)
    }

    class ViewHolder private constructor(private val binding : ItemProductBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(
            item: ProductItemData,
            clickListener: ProductItemClickListener,
            position: Int,
            fragment: ListFragment
        ) {
            binding.item = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
            when(position){
                10,20,30->{
                    binding.rvRecommend.visibility = View.VISIBLE
                    setRecyclerviewAdapter(item, fragment)
                }
                else->{ binding.rvRecommend.visibility = View.GONE}
            }
        }

        private fun setRecyclerviewAdapter(item: ProductItemData, fragment: ListFragment) {
            val adapter = RecommendListAdapter(RecommendItemClickListener { data, _->
                Timber.tag("setRecyclerviewAdapter").d("$data")
                val action = ListFragmentDirections.actionListFragmentToDetailFragment(data.imageUrl, data.productTitle)
                fragment.findNavController().navigate(action)
            })
            binding.rvRecommend.adapter = adapter
            adapter.submitList(item.recommendListData)
        }

        companion object{
            fun from(parent: ViewGroup) : ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemProductBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }


}