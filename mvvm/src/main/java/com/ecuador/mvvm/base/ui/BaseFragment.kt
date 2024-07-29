package com.ecuador.mvvm.base.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ecuador.mvvm.base.service.UiService
import com.ecuador.mvvm.base.util.ProgressDialogFragment


abstract class BaseFragment<DB : ViewDataBinding> : Fragment(), UiService {

    var initEventBus = false
    lateinit var mBinding: DB
    var srl : SwipeRefreshLayout? = null
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

    var progressDialogFragment : ProgressDialogFragment? = null
    override fun showLoading() {
        if(progressDialogFragment==null){
            progressDialogFragment = ProgressDialogFragment()
        }
        progressDialogFragment?.show(parentFragmentManager,"")
    }

    override fun dismissDialog() {
        progressDialogFragment?.dismiss()
        srl?.isRefreshing = false
    }

    override fun onDestroy() {
        super.onDestroy()

    }
}