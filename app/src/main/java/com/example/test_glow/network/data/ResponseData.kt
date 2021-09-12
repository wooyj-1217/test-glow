package com.example.test_glow.network.data

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize


// API_PRODUCTS 관련 DATA CLASS
@JsonClass(generateAdapter = true)
data class ResponseProductData(
    var products : List<ProductItemData>
)

@Parcelize
@JsonClass(generateAdapter = true)
data class ProductItemData(
    val idProduct: Int,
    val idBrand: Int,
    val productTitle: String,
    val volume : String,
    val price: Int? = 0,
    val productScore: Float,
    val ratingAvg: Double,
    val productRank: String,
    val rankChange: String,
    val rankChangeType: String,
    val reviewCount: String,
    val imageUrl: String,
    val brand: BrandItemData,
    var recommendListData: List<RecommendItemData>?
) : Parcelable {
    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<ProductItemData>() {
            override fun areItemsTheSame(
                oldItem: ProductItemData,
                newItem: ProductItemData
            ): Boolean = oldItem.idProduct == newItem.idProduct
            override fun areContentsTheSame(
                oldItem: ProductItemData,
                newItem: ProductItemData
            ): Boolean =  oldItem == newItem

        }
    }
}

@Parcelize
@JsonClass(generateAdapter = true)
data class BrandItemData(
    val idBrand: Int,
    val brandTitle: String,
    val imageUrl: String
) : Parcelable{

}

// API_RECOMMEND 관련 DATA CLASS
@JsonClass(generateAdapter = true)
data class ResponseRecommendData(
    val recommend1 : List<RecommendItemData>,
    val recommend2 : List<RecommendItemData>,
    val recommend3 : List<RecommendItemData>
)

@Parcelize
@JsonClass(generateAdapter = true)
data class RecommendItemData(
    val idProduct : Int,
    val productTitle : String,
    val ratingAvg : Double,
    val reviewCount: String,
    val imageUrl : String
) : Parcelable{
    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<RecommendItemData>() {
            override fun areItemsTheSame(
                oldItem: RecommendItemData,
                newItem: RecommendItemData
            ): Boolean = oldItem.idProduct == newItem.idProduct
            override fun areContentsTheSame(
                oldItem: RecommendItemData,
                newItem: RecommendItemData
            ): Boolean =  oldItem == newItem

        }
    }
}