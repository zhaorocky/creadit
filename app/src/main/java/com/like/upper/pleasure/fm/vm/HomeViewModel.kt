package com.like.upper.pleasure.fm.vm


import androidx.lifecycle.MutableLiveData
import com.like.upper.pleasure.net.apiService
import com.ecuador.mvvm.base.base.ResultState
import com.ecuador.mvvm.base.net.request
import com.ecuador.mvvm.base.ui.vm.BaseViewModel
import com.like.upper.pleasure.entity.HomeInfo
import com.like.upper.pleasure.entity.ProductInfo

class HomeViewModel : BaseViewModel(){

    val homeResult = MutableLiveData<ResultState<HomeInfo?>>()

    fun home(){
        val map = HashMap<String,String>()
        request({ apiService.home(setBaseData(map))},homeResult)
    }

}