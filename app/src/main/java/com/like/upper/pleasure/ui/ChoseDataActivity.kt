package com.like.upper.pleasure.ui

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.ecuador.mvvm.base.net.observeData
import com.ecuador.mvvm.base.net.parseState
import com.ecuador.mvvm.base.ui.BaseVmActivity
import com.like.upper.pleasure.R
import com.like.upper.pleasure.adapter.ChoseDataAdapter
import com.like.upper.pleasure.databinding.ActivityChoseDataBinding
import com.like.upper.pleasure.entity.DictionaryInfo
import com.like.upper.pleasure.entity.TypeChoseData
import com.like.upper.pleasure.ui.vm.ChoseDataViewModel
import com.like.upper.pleasure.view.onclick.OnItemDataClick


class ChoseDataActivity : BaseVmActivity<ChoseDataViewModel,ActivityChoseDataBinding>() {
    
    override fun layoutId() = R.layout.activity_chose_data

    private var listData = mutableListOf<DictionaryInfo>()
    private val genderAdapter  by lazy {
        ChoseDataAdapter(this,listData)
    }

    var type = 0
    var choseIndex = 0
    override fun initView() {

        type = intent.getIntExtra("type",0)

        mBinding.rvSpinner.layoutManager = LinearLayoutManager(this)
        genderAdapter.setItemCLickListener(object : OnItemDataClick{
            override fun onLickListener(position: Int) {
                choseIndex = position
            }
        })
        mBinding.rvSpinner.adapter = genderAdapter

        if(type == TypeChoseData.SEX.value){
            mViewModel.getGender(TypeChoseData.SEX.data)
        }else if(type == TypeChoseData.RELATIONG.value){
            mViewModel.getGender(TypeChoseData.RELATIONG.data)
        }else if(type == TypeChoseData.COLLECTIONTYPE.value){
            mViewModel.getGender(TypeChoseData.COLLECTIONTYPE.data)
        }else if(type == TypeChoseData.BANKLIST.value){
            mViewModel.getGender(TypeChoseData.BANKLIST.data)
        }else if(type == TypeChoseData.BANKNAME.value){
            mViewModel.getGender(TypeChoseData.BANKNAME.data)
        }

        mBinding.lyChoseData.setOnClickListener {
            finish()
        }



        mViewModel.result.observeData(this){
            parseState(it,{list ->
                Log.i("ChoseDataActivitysssss", list?.size.toString())
                list?.let {
                    listData.clear()
                    listData.addAll(it)
                    mBinding.rvSpinner.adapter = genderAdapter
                }
            },{
                showErrorMessage(it.error)
                finish()
            })
        }

        mBinding.tvAccept.setOnClickListener {
            startAct(choseIndex)
        }
        mBinding.tvCancel.setOnClickListener {
            finish()
        }
    }

    private fun startAct(index : Int){
        val intent = Intent(this,StartActivity::class.java)
        listData[index].let {
            intent.putExtra("value",it.cordlessPaperworkForeignMan)
            intent.putExtra("data",it.illPlainLameEnquiry.toString())
        }
        setResult(Activity.RESULT_OK,intent)
        finish()
    }


}