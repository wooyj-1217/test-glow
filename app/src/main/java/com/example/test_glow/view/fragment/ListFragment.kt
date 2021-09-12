package com.example.test_glow.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.example.test_glow.databinding.FragmentListBinding
import com.example.test_glow.network.utility.Status
import com.example.test_glow.viewmodel.ListViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ListFragment : Fragment() {

    private var _binding: FragmentListBinding?= null
    private val binding get() = _binding!!
    private val viewModel : ListViewModel by viewModels()

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
        observeViewModel()
        return binding.root
    }

    private fun observeViewModel(){

        viewModel.getRecommendList().asLiveData().observe(viewLifecycleOwner){
            when(it.status){
                Status.LOADING->{}
                Status.ERROR->{}
                Status.SUCCESS->{
                    Timber.tag(TAG).d("${it.data}")
                }
            }
        }

        viewModel.productsList.observe(viewLifecycleOwner){
            when(it.status){
                Status.LOADING->{}
                Status.ERROR->{}
                Status.SUCCESS->{
                    Timber.tag(TAG).d("${it.data}")
                }
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}