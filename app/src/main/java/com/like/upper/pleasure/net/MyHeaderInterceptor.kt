package com.like.upper.pleasure.net

import com.ecuador.mvvm.base.util.SharedPreferencesHelper
import com.like.upper.pleasure.App
import okhttp3.Interceptor
import okhttp3.Response

class MyHeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
            val sharedPreferencesHelper = SharedPreferencesHelper.getInstance(App.getAppContext())
            val builder = chain.request().newBuilder()
            builder.addHeader("livingPinkHeroineIce", "212")
            builder.addHeader("peacefulPersonalHotelEachRiver", "googleplay")
            builder.addHeader("merchantLongHimBritishClinic", "1.0.0")
            builder.addHeader("preciousBirdcageMessageBrightCentimetre", "100")
            builder.addHeader("eachGooseCrazySmell", "deviceId")
            builder.addHeader("freshBestPowerPhysician", "imei")
            builder.addHeader("formerFemaleCloudMagazine", "imei")
            sharedPreferencesHelper.token?.let {
                    builder.addHeader("emptyBasketBeauty", it)
            }
            sharedPreferencesHelper.userId?.let {
                    builder.addHeader("uglyReasonClearMeans", it)
            }
            //builder.addHeader("time", System.currentTimeMillis().toString())
            return chain.proceed(builder.build())
    }


}