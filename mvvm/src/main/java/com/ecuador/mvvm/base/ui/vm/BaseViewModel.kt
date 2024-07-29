package com.ecuador.mvvm.base.ui.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecuador.mvvm.base.util.SharedPreferencesHelper
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

open class BaseViewModel : ViewModel() {

    var loadingStatus = MutableLiveData<Boolean>()
    var errorStatus = MutableLiveData<Boolean>()
    var backPress = MutableLiveData<Boolean>()

    open fun setBaseData(map : HashMap<String,String>) : HashMap<String,String> {
        map["flamingTail"] = "es"
        map["asleepSelfCrazySummerSauce"] = "0.0,0.0"
        map["ripeBelgiumEssayNeedle"] = "212"
        //map["privateAction"] = "ea04e531deaea08eba6105d2e13b4195"
        return map
    }

    open fun createPartFromString(value: String): RequestBody {
        return value.toRequestBody("text/plain".toMediaTypeOrNull())
    }

}