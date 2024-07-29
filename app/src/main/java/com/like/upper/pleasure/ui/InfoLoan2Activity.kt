package com.like.upper.pleasure.ui

import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import com.ecuador.mvvm.base.ui.BaseVmActivity
import com.like.upper.pleasure.R
import com.like.upper.pleasure.databinding.ActivityInfoLoan2Binding
import com.like.upper.pleasure.fm.InfoImageActivity
import com.like.upper.pleasure.fm.vm.InfoViewModel

class InfoLoan2Activity : BaseVmActivity<InfoViewModel,ActivityInfoLoan2Binding>() {

    override fun layoutId() = R.layout.activity_info_loan2

    var name1 : String = ""
    var name2 : String = ""
    var phoneNum1 : String = ""
    var phoneNum2 : String = ""
    var relation1 : String = ""
    var relation2 : String = ""

    override fun initView() {

        mBinding.viewHeader.getLifrImage().setOnClickListener {
            finish()
        }

        mBinding.tvSubmit.setOnClickListener {
            startActivity(Intent(this, InfoImageActivity::class.java))
        }



    }

    private val resultRelation1 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if(result.resultCode == RESULT_OK){

        }
    }

    private val resultRelation2 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if(result.resultCode == RESULT_OK){

        }
    }

}