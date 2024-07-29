package com.like.upper.pleasure.fm.vm



import androidx.lifecycle.MutableLiveData
import com.ecuador.mvvm.base.base.ResultState
import com.ecuador.mvvm.base.net.request
import com.ecuador.mvvm.base.ui.vm.BaseViewModel
import com.ecuador.mvvm.base.util.SharedPreferencesHelper
import com.like.upper.pleasure.App
import com.like.upper.pleasure.entity.BaseUserInfo
import com.like.upper.pleasure.entity.HomeInfo
import com.like.upper.pleasure.net.apiService

class MineViewModel : BaseViewModel(){
    val homeResult = MutableLiveData<ResultState<HomeInfo?>>()

    fun home(){
        val map = HashMap<String,String>()

        map["ripeBelgiumEssayNeedle"] = ""
        request({ apiService.home(setBaseData(map))},homeResult)
    }

    val baseUserInfoResult = MutableLiveData<ResultState<BaseUserInfo?>>()
    fun getBaseInfo(){
        val map = HashMap<String,String>()
        map["uglyReasonClearMeans"] = SharedPreferencesHelper.getInstance(App.getInstance()).userId.toString()
        request({ apiService.getBaseInfo(setBaseData(map))},baseUserInfoResult)
    }

}