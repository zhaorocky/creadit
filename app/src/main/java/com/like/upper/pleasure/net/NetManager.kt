package com.like.upper.pleasure.net


import com.ecuador.mvvm.base.net.BaseNetConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit


val apiService by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
    NetManager.INSTANCE.getApi(ApiService::class.java,NetConfig.BASE_URL)
}
class NetManager : BaseNetConfig() {
    companion object{
        val INSTANCE by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            NetManager()
        }
    }

    override fun setHttpClientBuild() : OkHttpClient{
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(MyHeaderInterceptor())
            .addInterceptor(loggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }

}