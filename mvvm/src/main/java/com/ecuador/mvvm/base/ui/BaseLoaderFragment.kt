package com.ecuador.mvvm.base.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.ecuador.mvvm.base.service.UiService


abstract class BaseLoaderFragment<DB : ViewDataBinding> : Fragment(), UiService {

    var initEventBus = false
    lateinit var mBinding: DB

    lateinit var  loaderFragment : Fragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater,layoutId(),container,false)

        return mBinding.root
    }

    private var isFirstShow = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isFirstShow = true
        setVm()

        initView()
    }

    override fun setVm() {

    }

    override fun showLoading() {

    }

    override fun dismissDialog() {

    }

    override fun onDestroy() {
        super.onDestroy()

    }
}