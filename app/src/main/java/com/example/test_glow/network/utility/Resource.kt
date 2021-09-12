package com.example.test_glow.network.utility

/**
 *
 *   @comment Retrofit 데이터를 받은 후에 결과값이 성공인지, 실패인지, 로딩상태인지를 분기할 수 있도록 함.
 *   @see https://developer.android.com/jetpack/guide?hl=ko#fetch-data 참고하였음.
 *
 */



enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}

/**
 *  data class : [Resource]
 *
 *   @param status : enum class인 Status의 상태값
 *   @param data : network 통신 결과로 받은 data 값
 *   @param message : network 통신 실패 시 생성되는 message 값
 *
 */

data class Resource<out T>(
    val status : Status,
    val data : T?,
    val message : String?
){
    companion object{
        fun <T> success(data:T?) : Resource<T>{
            return Resource(Status.SUCCESS, data, null)
        }
        fun <T> error(msg:String, data:T?) : Resource<T>{
            return Resource(Status.ERROR, data, msg)
        }
        fun <T> loading(data:T?) : Resource<T>{
            return Resource(Status.LOADING, data, null)
        }
    }
}