package com.ecuador.mvvm.base.service

interface UiService {

    fun layoutId() : Int

    fun setVm()

    fun initView()

    fun showLoading()

    fun dismissDialog()
}