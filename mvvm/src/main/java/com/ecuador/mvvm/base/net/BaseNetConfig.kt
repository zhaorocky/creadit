package com.ecuador.mvvm.base.net

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

open class BaseNetConfig {

    companion object{
        val INSTANCE by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            BaseNetConfig()
        }
    }

    fun <T> getApi(serviceClass: Class<T>,baseUrl : String) : T{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(setHttpClientBuild())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(serviceClass)
    }

    open fun setHttpClientBuild() : OkHttpClient{
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            //.addInterceptor()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(10,TimeUnit.SECONDS)
            .writeTimeout(10,TimeUnit.SECONDS)
            .readTimeout(10,TimeUnit.SECONDS)
            .build()
    }

}