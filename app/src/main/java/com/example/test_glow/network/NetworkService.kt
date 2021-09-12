package com.example.test_glow.network

import com.example.test_glow.network.data.ResponseProductData
import com.example.test_glow.network.data.ResponseRecommendData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NetworkService {

    @GET(ApiUri.API_PRODUCTS)
    suspend fun productsList(@Path("page") page:String) : Response<ResponseProductData>

    @GET(ApiUri.API_RECOMMEND)
    suspend fun recommendList() : Response<ResponseRecommendData>

}

