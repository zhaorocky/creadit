package com.like.upper.pleasure.ui

import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.NavHostFragment
import com.ecuador.mvvm.base.ui.BaseVmActivity
import com.like.upper.pleasure.R
import com.like.upper.pleasure.databinding.ActivityInfoBinding
import com.like.upper.pleasure.fm.vm.InfoViewModel
import com.like.upper.pleasure.view.NetWorkDialog

class InfoBaseActivity : BaseVmActivity<InfoViewModel,ActivityInfoBinding>() {
    override fun showNetTimeOutDialog() {
        NetWorkDialog.showNetTimeOutDialog(this)
    }

    override fun layoutId() = R.layout.activity_info_base


    override fun initView() {
//        val callback = object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                val navHostFragment = supportFragmentManager.findFragmentById(R.id.fcv_infoAct) as NavHostFragment
//                val navController = navHostFragment.navController
//                if (!navController.popBackStack()) {
//                    finish()
//                }
//            }
//        }
//
//        // 将回调添加到 onBackPressedDispatcher
//        onBackPressedDispatcher.addCallback(this, callback)

    }


}