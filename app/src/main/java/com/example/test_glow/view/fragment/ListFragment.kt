package com.example.test_glow.view.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.test_glow.R
import com.example.test_glow.databinding.FragmentListBinding
import com.example.test_glow.network.data.ResponseProductData
import com.example.test_glow.network.data.ResponseRecommendData
import com.example.test_glow.network.utility.Status
import com.example.test_glow.view.adapter.ProductItemClickListener
import com.example.test_glow.view.adapter.ProductListAdapter
import com.example.test_glow.viewmodel.ListViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ListFragment : Fragment() {

    private var _binding: FragmentListBinding?= null
    private val binding get() = _binding!!
    private val viewModel : ListViewModel by viewModels()

    lateinit var recommendList : ResponseRecommendData
    private lateinit var productListAdapter : ProductListAdapter

    private val TAG = javaClass.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = viewModel
        }
        setRecyclerViewAdapter()
        observeViewModel()
        return binding.root
    }

    private fun setRecyclerViewAdapter(){
        productListAdapter = ProductListAdapter( ProductItemClickListener { data, view->
            Timber.tag(TAG).d("$data")
            val action = ListFragmentDirections.actionListFragmentToDetailFragment(data.imageUrl, data.productTitle)
            findNavController().navigate(action)
        }, this)
        binding.rvList.adapter = productListAdapter
        binding.rvList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                //TODO("infinite Scroll 작업 필요")
            }
        })
    }

    private fun observeViewModel(){

        viewModel.getRecommendList().asLiveData().observe(viewLifecycleOwner){
            when(it.status){
                Status.LOADING->{}
                Status.ERROR->{}
                Status.SUCCESS->{
                    recommendList = it.data!!
                }
            }
        }

        viewModel.productsList.observe(viewLifecycleOwner){
            when(it.status){
                Status.LOADING->{}
                Status.ERROR->{}
                Status.SUCCESS->{
                    setDataForRecyclerView(it.data)
                }
            }
        }

    }

    private fun setDataForRecyclerView(data: ResponseProductData?) {
        if (data != null) {
            for(i in data.products.indices){
                if(i == 10) {
                    data.products[i].recommendListData = recommendList.recommend1
                }
                if(i == 20) {
                    data.products[i].recommendListData = recommendList.recommend2
                }
                if(i == 30) {
                    data.products[i].recommendListData = recommendList.recommend3
                }
            }
            productListAdapter.submitList(data.products)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEvent()
    }

    private fun initEvent(){

        //endless list 이벤트
        if(viewModel.pageString.value.toInt() < 3){
            Timber.tag(TAG).d("${viewModel.pageString.value.toInt() + 1}")
            viewModel.pageString.value = (viewModel.pageString.value.toInt() + 1).toString()
        }

    }

}