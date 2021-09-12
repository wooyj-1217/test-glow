package com.example.test_glow.network

import com.example.test_glow.network.data.ProductItemData
import com.example.test_glow.network.data.ResponseProductData
import com.example.test_glow.network.data.ResponseRecommendData
import com.example.test_glow.network.utility.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okhttp3.RequestBody
import javax.inject.Inject

class ProductsListUseCase @Inject constructor(private val repo: NetworkRepository){
    fun execute(page:String): Flow<Resource<ResponseProductData>> = flow {
        emit(Resource.loading(null))
        emit(setResultBody(page))
    }.catch{ e->
        emit(Resource.error(e.localizedMessage, null))
    }

    private suspend fun setResultBody(page:String):Resource<ResponseProductData>{
        var resultBody = mutableListOf<ProductItemData>()
        for(i in 1..page.toInt()) {
            repo.productsList(i.toString()).let {
                if (it.isSuccessful) {
                    it.body()!!.products.forEach {data->
                        resultBody.add(data)
                    }
                } else {
                    return Resource.error(it.errorBody().toString(), null)
                }
            }
        }
        val result = ResponseProductData(resultBody)
        return Resource.success(result)
    }

}

class RecommendListUseCase @Inject constructor(private val repo: NetworkRepository){
    fun execute(): Flow<Resource<ResponseRecommendData>> = flow {
        emit(Resource.loading(null))
        emit(repo.recommendList().let {
            if (it.isSuccessful) {
                Resource.success(it.body())
            } else {
                Resource.error(it.errorBody().toString(), null)
            }
        }
        )
    }.catch{ e->
        emit(Resource.error(e.localizedMessage, null))
    }
}