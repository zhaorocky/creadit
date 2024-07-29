package com.like.upper.pleasure.fm


import android.content.Intent
import com.ecuador.mvvm.base.ui.BaseVmFragment
import com.ecuador.mvvm.base.util.SharedPreferencesHelper
import com.like.upper.pleasure.R
import com.like.upper.pleasure.databinding.FragmentHomeDefBinding
import com.like.upper.pleasure.fm.vm.HomeViewModel
import com.like.upper.pleasure.ui.InfoBaseActivity
import com.like.upper.pleasure.ui.LoanChoseAmountActivity
import com.like.upper.pleasure.ui.LoginActivity
import com.like.upper.pleasure.util.setOnSingleClickListener


class HomeDefFragment : BaseVmFragment<HomeViewModel, FragmentHomeDefBinding>() {

    override fun layoutId() = R.layout.fragment_home_def

    override fun initView() {

        mBinding.tvDefFmApply.setOnSingleClickListener {
            val  sharedPreferencesHelper = SharedPreferencesHelper.getInstance(requireContext())
            if(sharedPreferencesHelper.token?.isBlank() == true){
                startLoginActivity()
            }else{
                startInfoActivity()
            }
        }

    }

    private fun startLoginActivity(){
        startActivity(Intent(context, LoginActivity::class.java))
    }

    private fun startInfoActivity(){
        startActivity(Intent(context, InfoBaseActivity::class.java))
    }

    override fun showLoading() {

    }

    override fun dismissDialog() {

    }



}