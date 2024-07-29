package com.like.upper.pleasure.ui.vm

import androidx.lifecycle.MutableLiveData
import com.like.upper.pleasure.net.apiService
import com.ecuador.mvvm.base.base.ResultState
import com.ecuador.mvvm.base.net.request
import com.ecuador.mvvm.base.ui.vm.BaseViewModel
import com.like.upper.pleasure.entity.DictionaryInfo

class ChoseDataViewModel : BaseViewModel(){

    val result = MutableLiveData<ResultState<MutableList<DictionaryInfo>?>>()

    fun getGender(type : String){
        val map = HashMap<String,String>()
        map["spiritualSurroundingLeaf"] = type
        request({ apiService.getGender(setBaseData(map))},result)
    }
}