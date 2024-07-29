package com.like.upper.pleasure.ui.vm

import androidx.lifecycle.MutableLiveData
import com.like.upper.pleasure.net.apiService
import com.ecuador.mvvm.base.base.ResultState
import com.ecuador.mvvm.base.net.request
import com.ecuador.mvvm.base.ui.vm.BaseViewModel
import com.ecuador.mvvm.base.util.SharedPreferencesHelper
import com.like.upper.pleasure.App
import com.like.upper.pleasure.entity.ProductInfo
import com.like.upper.pleasure.entity.TryLoanResultInfo

class ChoseAmountViewModel : BaseViewModel(){

    val resultProductInfo = MutableLiveData<ResultState<ProductInfo?>>()

    fun getProductInfo(){
        val map = HashMap<String,String>()

        request({ apiService.getProductList(setBaseData(map))},resultProductInfo)
    }

    val tryLoanResult = MutableLiveData<ResultState<TryLoanResultInfo?>>()

    fun tryLoan(productId : String?,detailId : String?,applyAmount : String?){
        val map = HashMap<String,String>()
        productId?.let {
            map["unfairHostessDevotionDelightedIreland"] = productId
        }

        detailId?.let {
            map["bestDifferentIllChocolate"] = detailId
        }

        applyAmount?.let {
            map["leftCathedralBelly"] = applyAmount
        }
        map["uglyReasonClearMeans"] = SharedPreferencesHelper.getInstance(App.getInstance()).userId.toString()
        request({ apiService.tryLoan(setBaseData(map))},tryLoanResult)
    }

    val preResult = MutableLiveData<ResultState<TryLoanResultInfo?>>()
    fun preSubmit(productId : String?,detailId : String?,applyAmount : String?){
        val map = HashMap<String,String>()
        productId?.let {
            map["unfairHostessDevotionDelightedIreland"] = productId
        }

        detailId?.let {
            map["bestDifferentIllChocolate"] = detailId
        }

        applyAmount?.let {
            map["leftCathedralBelly"] = applyAmount
        }
        map["uglyReasonClearMeans"] = SharedPreferencesHelper.getInstance(App.getInstance()).userId.toString()
        request({ apiService.preSubmitLoan(setBaseData(map))},preResult)
    }
}