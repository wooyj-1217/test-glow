package com.example.test_glow.di

import android.content.Context
import android.provider.Settings
import com.example.test_glow.BuildConfig
import com.example.test_glow.network.ApiUri
import com.example.test_glow.network.NetworkRepository
import com.example.test_glow.network.NetworkService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.security.cert.CertificateException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.*


@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    private const val CONNECT_TIMEOUT = 20L
    private const val WRITE_TIMEOUT = 20L
    private const val READ_TIMEOUT = 20L

    @Provides
    @Singleton
    fun provideBodyLoggingInterceptor() : HttpLoggingInterceptor =
        HttpLoggingInterceptor { message -> if(message.length < 16*1024*1024) {
            Timber.tag("NetworkModule : ").d(message)}
            //log가 너무 길면 EOF 오류 발생하므로 제한해주어야 한다. 디버깅 옵션에서 로거 버퍼 크기를 16M로 설정하자.
        }.setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun provideTrustManagerArray() : Array<TrustManager>
            = arrayOf<TrustManager>(object : X509TrustManager {
        @Throws(CertificateException::class)
        override fun checkClientTrusted(
            chain: Array<java.security.cert.X509Certificate>,
            authType: String
        ) {
        }

        @Throws(CertificateException::class)
        override fun checkServerTrusted(
            chain: Array<java.security.cert.X509Certificate>,
            authType: String
        ) {
        }

        override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
            return arrayOf()
        }
    })

    @Provides
    @Singleton
    fun provideNullHostNameVerifier() : HostnameVerifier = HostnameVerifier { _, _ -> true}


    @Provides
    @Singleton
    fun provideSslSocketFactory(trustAllCerts:Array<TrustManager>): SSLSocketFactory {
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, java.security.SecureRandom())
        // Create an ssl socket factory with our all-trusting manager
        return sslContext.socketFactory
    }

    @Singleton
    @Provides
    fun provideOkHttpClientForApi(bodyLog: HttpLoggingInterceptor,
                                  sslSocketFactory : SSLSocketFactory,
                                  trustAllCerts:Array<TrustManager>,
                                  nullHostNameVerifier: HostnameVerifier,
    ): OkHttpClient
            = OkHttpClient.Builder()
        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)                                           // 연결 타임아웃 시간 설정
        .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)                                               // 쓰기 타임아웃 시간 설정
        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)                                                 // 읽기 타임아웃 시간 설정
//        .addNetworkInterceptor(Interceptor { chain ->                                                // Add Header
//            val requestBuilder = chain.request().newBuilder()
//                .addHeader("Accept", "application/json")
//                .header("Content-Type", "application/json")
//            chain.proceed(requestBuilder.build())
//        })
        .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
        .hostnameVerifier(nullHostNameVerifier)                                             // SSL 인증서의 도메인이름 검증을 생략하기 위해 추가
        .addInterceptor(bodyLog)                                                            // 로그 찍는 용도
        .build()

    @Singleton
    @Provides
    fun provideRetrofitServiceApi(okHttpClient: OkHttpClient): Retrofit
            = Retrofit.Builder()
        .baseUrl(ApiUri.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create().asLenient())
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit:Retrofit) : NetworkService = retrofit.create(NetworkService::class.java)

    @Singleton
    @Provides
    fun provideApiRepository(service: NetworkService) : NetworkRepository = NetworkRepository(service)


}