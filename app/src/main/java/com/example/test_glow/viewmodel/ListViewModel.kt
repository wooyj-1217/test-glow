package com.example.test_glow.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.test_glow.network.ProductsListUseCase
import com.example.test_glow.network.RecommendListUseCase
import com.example.test_glow.network.data.ProductItemData
import com.example.test_glow.network.data.RecommendItemData
import com.example.test_glow.network.data.ResponseProductData
import com.example.test_glow.network.utility.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    application: Application,
    private val productsListUseCase: ProductsListUseCase,
    private val recommendListUseCase: RecommendListUseCase,
    private val savedStateHandle: SavedStateHandle
): AndroidViewModel(application) {

    //page, 검색어 정보
    private val pageString: MutableStateFlow<String> = MutableStateFlow(
        savedStateHandle.get("page")?: "1"
    )

    val productsList : LiveData<Resource<ResponseProductData>>
        get() = _res
    private val _res = pageString.flatMapLatest { page->
        productsListUseCase.execute(page)
    }.asLiveData()

    fun getRecommendList() = recommendListUseCase.execute()

}