package com.like.upper.pleasure.fm.vm

import androidx.lifecycle.MutableLiveData
import com.like.upper.pleasure.net.apiService
import com.ecuador.mvvm.base.base.ResultState
import com.ecuador.mvvm.base.net.request
import com.ecuador.mvvm.base.ui.vm.BaseViewModel
import com.ecuador.mvvm.base.util.SharedPreferencesHelper
import com.like.upper.pleasure.App
import com.like.upper.pleasure.entity.BaseUserInfo

class InfoViewModel : BaseViewModel(){


    val loginResult = MutableLiveData<ResultState<String?>>()

    fun saveBase(name1 : String,name2 : String,email : String,date : String,sex : String){
        val map = HashMap<String,String>()
        map["tinyCrossroadsBellyTiresomeHat"] = name1
        map["chemicalEntranceMusicalChild"] = name2
        map["bitterHalfKangaroo"] = date
        map["strongSpringCock"] = email
        map["hopelessZooHotMidnightTyphoon"] = sex
        map["uglyReasonClearMeans"] = SharedPreferencesHelper.getInstance(App.getInstance()).userId.toString()
        request({ apiService.saveBaseInfo(setBaseData(map))},loginResult)
    }

    fun saveContract(name1 : String,name2 : String,num1 : String,num2 : String,r1 : String,r2 : String,){
        val map = HashMap<String,String>()
        map["seniorPedestrianElectricalRain"] = r1
        map["maleMostTheme"] = name1
        map["lameVaseSuchDeadlineMarket"] = num1
        map["followingFamiliarAccident"] = r2
        map["merryAidsServant"] = name2
        map["juicySureAuthorBusiness"] = num2
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