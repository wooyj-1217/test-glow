package com.example.test_glow.network.data

import com.squareup.moshi.JsonClass


// API_PRODUCTS 관련 DATA CLASS
@JsonClass(generateAdapter = true)
data class ResponseProductData(
    var products : List<ProductItemData>
)

@JsonClass(generateAdapter = true)
data class ProductItemData(
    val idProduct: Int,
    val idBrand: Int,
    val productTitle: String,
    val volume : String,
    val price: Int,
    val productScore: Float,
    val ratingAvg: Double,
    val productRank: String,
    val rankChange: String,
    val rankChangeType: String,
    val reviewCount: String,
    val imageUrl: String,
    val brand: BrandItemData,
    var recommendListData: List<RecommendItemData>?
)

@JsonClass(generateAdapter = true)
data class BrandItemData(
    val idBrand: Int,
    val brandTitle: String,
    val imageUrl: String
)

// API_RECOMMEND 관련 DATA CLASS
@JsonClass(generateAdapter = true)
data class ResponseRecommendData(
    val recommend1 : List<RecommendItemData>,
    val recommend2 : List<RecommendItemData>,
    val recommend3 : List<RecommendItemData>
)

@JsonClass(generateAdapter = true)
data class RecommendItemData(
    val idProduct : Int,
    val productTitle : String,
    val ratingAvg : Double,
    val reviewCount: String,
    val imageUrl : String
)