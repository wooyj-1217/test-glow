package com.example.test_glow.network

import javax.inject.Inject

class NetworkRepository @Inject constructor(private val service: NetworkService) {

    suspend fun productsList(page: String) = service.productsList(page)
    suspend fun recommendList() = service.recommendList()

}