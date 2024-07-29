package com.like.upper.pleasure.ui.vm

import androidx.lifecycle.MutableLiveData
import com.like.upper.pleasure.net.apiService
import com.ecuador.mvvm.base.base.ResultState
import com.ecuador.mvvm.base.net.request
import com.ecuador.mvvm.base.ui.vm.BaseViewModel
import com.like.upper.pleasure.entity.CodeResult
import com.like.upper.pleasure.entity.LoginResult

class LoginViewModel : BaseViewModel(){


    val loginResult = MutableLiveData<ResultState<LoginResult?>>()
    val codeResult = MutableLiveData<ResultState<CodeResult?>>()

    fun sendVerifyCode(phone : String){
        val map = HashMap<String,String>()
        map["fancyUnsuccessfulBuddhistIncorrectTaxi"] = phone
        request({ apiService.sendVerifyCode(setBaseData(map))},codeResult)
    }

    fun login(code : String,phone : String){
        val map = HashMap<String,String>()
        map["digestBedroomStraitDusk"] = code
        map["fancyUnsuccessfulBuddhistIncorrectTaxi"] = phone
        request({ apiService.login(setBaseData(map))},loginResult)
    }
}