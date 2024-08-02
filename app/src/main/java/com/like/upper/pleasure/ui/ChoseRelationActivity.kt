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
import com.like.upper.pleasure.ui.vm.ChoseDataViewModel
import com.like.upper.pleasure.view.NetWorkDialog
import com.like.upper.pleasure.view.onclick.OnItemDataClick


class ChoseRelationActivity : BaseVmActivity<ChoseDataViewModel,ActivityChoseDataBinding>() {
    override fun showNetTimeOutDialog() {
        NetWorkDialog.showNetTimeOutDialog(this)
    }

    override fun layoutId() = R.layout.activity_chose_data

    private var listData = mutableListOf<DictionaryInfo>()
    private val genderAdapter  by lazy {
        ChoseDataAdapter(this,listData)
    }

    var type = 0
    override fun initView() {

        type = intent.getIntExtra("type",0)

        mBinding.rvSpinner.layoutManager = LinearLayoutManager(this)
        mBinding.rvSpinner.adapter = genderAdapter


        genderAdapter.setItemCLickListener(object : OnItemDataClick{
            override fun onLickListener(position: Int) {
                startAct(position)
            }
        })

        mViewModel.result.observeData(this){
            parseState(it,{list ->
                Log.i("ChoseDataActivitysssss", list?.size.toString())
                list?.let {
                    listData.clear()
                    listData.addAll(it)
                    mBinding.rvSpinner.adapter = genderAdapter
                }
            },{
                errorAction(it.errCode,it.error)


            })
        }
    }

    private fun startAct(index : Int){
        val intent = Intent(this,StartActivity::class.java)
        listData[index].let {
            intent.putExtra("value",it.cordlessPaperworkForeignMan)
            intent.putExtra("data",it.illPlainLameEnquiry)
        }
        setResult(Activity.RESULT_OK,intent)
        finish()
    }


}