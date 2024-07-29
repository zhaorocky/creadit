package com.like.upper.pleasure.fm.vm

import androidx.lifecycle.MutableLiveData
import com.like.upper.pleasure.net.apiService
import com.ecuador.mvvm.base.base.ResultState
import com.ecuador.mvvm.base.net.request
import com.ecuador.mvvm.base.ui.vm.BaseViewModel
import com.ecuador.mvvm.base.util.SharedPreferencesHelper
import com.like.upper.pleasure.App
import com.like.upper.pleasure.entity.BaseUserInfo
import com.like.upper.pleasure.entity.ImageInfo
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class InfoImageModel : BaseViewModel(){


    val uploadResult = MutableLiveData<ResultState<String?>>()
    fun uploadImg(file : File?,type : String){
        file?.let {
            val pair = MultipartBody.Part.createFormData("heavyGarbageHappinessPhoto",it.name,it.asRequestBody())
            val appId = "212".toRequestBody("text/plain".toMediaTypeOrNull())
            val userId = SharedPreferencesHelper.getInstance(App.getInstance()).userId.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val gps = "0.0,0.0".toRequestBody("text/plain".toMediaTypeOrNull())
            val es = "es".toRequestBody("text/plain".toMediaTypeOrNull())
            val type = type.toRequestBody("text/plain".toMediaTypeOrNull())
            request({ apiService.uploadImg(pair,appId,userId,gps,es,type)},uploadResult)
        }
    }

    val baseImgInfoResult = MutableLiveData<ResultState<ImageInfo?>>()
    fun getBaseImgInfo(){
        val map = HashMap<String,String>()
        map["uglyReasonClearMeans"] = SharedPreferencesHelper.getInstance(App.getInstance()).userId.toString()
        request({ apiService.getImageInfo(setBaseData(map))},baseImgInfoResult)
    }

    val loginResult = MutableLiveData<ResultState<String?>>()

    fun saveCard(cardNum : String,){
        val map = HashMap<String,String>()
        map["youngPresentationDifficultFailureNeitherPhotograph"] = cardNum
        map["uglyReasonClearMeans"] = SharedPreferencesHelper.getInstance(App.getInstance()).userId.toString()
        request({ apiService.saveBaseInfo(setBaseData(map))},loginResult)
    }

    val baseUserInfoResult = MutableLiveData<ResultState<BaseUserInfo?>>()
    fun getBaseInfo(){
        val map = HashMap<String,String>()
        map["uglyReasonClearMeans"] = SharedPreferencesHelper.getInstance(App.getInstance()).userId.toString()
        request({ apiService.getBaseInfo(setBaseData(map))},baseUserInfoResult)
    }
}