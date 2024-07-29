package com.like.upper.pleasure.fm


import android.content.Intent
import com.ecuador.mvvm.base.ui.BaseVmFragment
import com.ecuador.mvvm.base.util.SharedPreferencesHelper
import com.like.upper.pleasure.R
import com.like.upper.pleasure.databinding.FragmentHomeDefBinding
import com.like.upper.pleasure.databinding.FragmentHomeLoansBinding
import com.like.upper.pleasure.fm.vm.HomeViewModel
import com.like.upper.pleasure.ui.InfoBaseActivity
import com.like.upper.pleasure.ui.LoanChoseAmountActivity
import com.like.upper.pleasure.ui.LoginActivity
import com.like.upper.pleasure.util.setOnSingleClickListener


class HomeLoansFragment : BaseVmFragment<HomeViewModel, FragmentHomeLoansBinding>() {

    override fun layoutId() = R.layout.fragment_home_loans

    override fun initView() {


    }

    override fun showLoading() {

    }

    override fun dismissDialog() {

    }



}