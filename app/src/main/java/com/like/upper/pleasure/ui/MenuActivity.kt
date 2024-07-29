package com.like.upper.pleasure.ui

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import com.ecuador.mvvm.base.ui.BaseVmActivity
import com.like.upper.pleasure.R
import com.like.upper.pleasure.adapter.MenuPagerAdapter
import com.like.upper.pleasure.databinding.ActivityMenuBinding
import com.like.upper.pleasure.ui.vm.MenuViewModel
import com.ecuador.mvvm.base.util.SharedPreferencesHelper


class MenuActivity : BaseVmActivity<MenuViewModel, ActivityMenuBinding>() {

    lateinit var sharedPreferencesHelper : SharedPreferencesHelper

    override fun layoutId() = R.layout.activity_menu
    override fun initView() {
        sharedPreferencesHelper = SharedPreferencesHelper.getInstance(this)
        mBinding.vpMenuAct.adapter = MenuPagerAdapter(this,3)
        mBinding.vpMenuAct.isUserInputEnabled = false
        mBinding.vpMenuAct.offscreenPageLimit = 3

        mBinding.btnMenuAct.setOnItemSelectedListener {
            when(it.itemId){
                R.id.fragmentHome ->{
                    choseIndex = 1
                    mBinding.vpMenuAct.setCurrentItem(0,true)
                }
                R.id.fragmentOrder ->{
                    mBinding.vpMenuAct.setCurrentItem(1,true)
                    if (sharedPreferencesHelper.token?.isNotBlank() == true){
                        mBinding.vpMenuAct.setCurrentItem(1,true)
                    }else{
                        toLogin()
                        choseIndex = 1
                        return@setOnItemSelectedListener false
                    }

                }
                R.id.fragmentMy ->{
                    mBinding.vpMenuAct.setCurrentItem(2,true)
                    if (sharedPreferencesHelper.token?.isNotBlank() == true){
                        mBinding.vpMenuAct.setCurrentItem(2,true)
                    }else{
                        toLogin()
                        choseIndex = 2
                        return@setOnItemSelectedListener false
                    }
                }
            }
            return@setOnItemSelectedListener true

        }
    }

    var choseIndex = 0
    private fun toLogin(){
        startActivity(Intent(this,LoginActivity::class.java))
    }

    override fun onStart() {
        super.onStart()
        if(sharedPreferencesHelper.token?.isBlank() == true){
            mBinding.vpMenuAct.setCurrentItem(choseIndex,true)
            mBinding.btnMenuAct.selectedItemId = R.id.fragmentHome
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {

    }



}