package com.like.upper.pleasure.ui.vm

import androidx.lifecycle.MutableLiveData
import com.like.upper.pleasure.net.apiService
import com.ecuador.mvvm.base.base.ResultState
import com.ecuador.mvvm.base.net.request
import com.ecuador.mvvm.base.ui.vm.BaseViewModel
import com.ecuador.mvvm.base.util.SharedPreferencesHelper
import com.like.upper.pleasure.App
import com.like.upper.pleasure.entity.BankAcountInfoItem

class InfoLoanViewModel : BaseViewModel(){

    val saveResult = MutableLiveData<ResultState<String?>>()

    fun saveBankInfo(collectionType : String,bankAccountType : String,bankCode : String,bankName : String,bankAccountNumber : String){
        val map = HashMap<String,String>()
        if(collectionType == "2"){
            map["briefMirrorHarbourPath"] = bankAccountNumber
        }else{
            map["moralReadingSunnySmog"] = bankAccountType
            map["bravePalaceAttractiveRecorder"] = bankName
            map["maleCuriousMiddle"] = bankCode
            map["readySeveralBasicRat"] = bankAccountNumber
        }
        map["uncertainMealPillowTechnique"] = collectionType
        map["uglyReasonClearMeans"] = SharedPreferencesHelper.getInstance(App.getInstance()).userId.toString()
        request({ apiService.saveBankInfo(setBaseData(map))},saveResult)
    }

    val bankResult = MutableLiveData<ResultState<MutableList<BankAcountInfoItem>?>>()

    fun getBankInfo(){
        val map = HashMap<String,String>()
        map["uglyReasonClearMeans"] = SharedPreferencesHelper.getInstance(App.getInstance()).userId.toString()
        request({ apiService.getBankInfo(setBaseData(map))},bankResult)
    }
}