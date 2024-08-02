package com.like.upper.pleasure.ui.vm

import androidx.lifecycle.MutableLiveData
import com.ecuador.mvvm.base.base.ResultState
import com.ecuador.mvvm.base.net.request
import com.ecuador.mvvm.base.ui.vm.BaseViewModel
import com.like.upper.pleasure.entity.CodeResult
import com.like.upper.pleasure.entity.UpdateInfo
import com.like.upper.pleasure.net.apiService

class MenuViewModel : BaseViewModel(){
    val updateInfo = MutableLiveData<ResultState<UpdateInfo?>>()

    fun getUpdateInfo(versionName : String,versionCode:String,imei:String){
        val map = HashMap<String,String>()
        map["versionName"] = versionName
        map["versionCode"] = versionCode
        map["imei"] = imei
        request({ apiService.getUpdateInfo(setBaseData(map))},updateInfo)
    }
}