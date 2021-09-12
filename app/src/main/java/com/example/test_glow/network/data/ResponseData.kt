package com.example.test_glow.network.data



// API_PRODUCTS 관련 DATA CLASS
data class ResponseProductData(
    var products : ArrayList<ProductItemData>
)

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
    val brand: BrandItemData
)

data class BrandItemData(
    val idBrand: Int,
    val brandTitle: String,
    val imageUrl: String
)