package com.like.upper.pleasure.fm


import com.ecuador.mvvm.base.ui.BaseVmFragment
import com.like.upper.pleasure.R
import com.like.upper.pleasure.databinding.FragmentOrderBinding
import com.like.upper.pleasure.fm.vm.MineViewModel
import com.like.upper.pleasure.view.NetWorkDialog


class OrderFragment : BaseVmFragment<MineViewModel, FragmentOrderBinding>() {

    override fun layoutId() = R.layout.fragment_order

    override fun initView() {

    }

    override fun showNetTimeOutDialog() {
        NetWorkDialog.showNetTimeOutDialog(requireContext())
    }

    override fun showLoading() {

    }

    override fun dismissDialog() {

    }


}