package com.like.upper.pleasure.fm


import android.annotation.SuppressLint
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.fragment.app.Fragment
import com.ecuador.mvvm.base.net.observeData
import com.ecuador.mvvm.base.net.parseState
import com.ecuador.mvvm.base.ui.BaseVmFragment
import com.like.upper.pleasure.R
import com.like.upper.pleasure.databinding.FragmentHomeBinding
import com.like.upper.pleasure.fm.vm.HomeViewModel
import com.like.upper.pleasure.util.NubmerUtils
import com.like.upper.pleasure.view.NetWorkDialog


class HomeFragment : BaseVmFragment<HomeViewModel, FragmentHomeBinding>() {

    override fun layoutId() = R.layout.fragment_home

    @SuppressLint("SetTextI18n")
    override fun initView() {

        srl = mBinding.srHomeFm
        mViewModel.homeResult.observeData(this) {
            parseState(it, { homeInfo ->
                if(homeInfo?.lessReplySafetyMusicalCompany.isNullOrBlank()){
                    mBinding.tvHomeFmMax.setText("$ "+9000)
                }else{
                    mBinding.tvHomeFmMax.setText("$ "+NubmerUtils.numStringToStringNum1000(homeInfo?.lessReplySafetyMusicalCompany))
                }
                if(homeInfo?.extraStranger.isNullOrBlank()){
                    mBinding.tvHomeFmTimes.setText("91"+activity?.getString(R.string.home_days))
                }else{
                    mBinding.tvHomeFmTimes.setText(homeInfo?.extraStranger+activity?.getString(R.string.home_days))
                }
                homeInfo?.possibleGlasshouseEveningStand?.let {
                    if(it.isNotBlank()&& it == "-1"){
                        mBinding.tvHomeFmStatus.visibility = View.GONE
                        mBinding.tvHomeFmToast.visibility = View.VISIBLE
                        mBinding.tvHomeMaxText.text = getString(R.string.home_max)
                        mBinding.tvHomeFmTimesText.text = getString(R.string.home_times)
                        replaceFragment(HomeDefFragment())
                    }else if(it == "0"){
                        mBinding.tvHomeFmStatus.visibility = View.VISIBLE
                        mBinding.tvHomeFmStatus.text = getString(R.string.home_review)
                        mBinding.tvHomeMaxText.text = getString(R.string.home_loans_amount)
                        mBinding.tvHomeFmTimesText.text = getString(R.string.home_loans_days)
                        replaceFragment(HomeReviewFragment())
                    }else if(it == "1"){
                        mBinding.tvHomeFmStatus.visibility = View.VISIBLE
                        mBinding.tvHomeFmStatus.text = getString(R.string.home_review)
                        mBinding.tvHomeMaxText.text = getString(R.string.home_loans_amount)
                        mBinding.tvHomeFmTimesText.text = getString(R.string.home_loans_days)
                        replaceFragment(HomeLoansFragment())
                    }
                }

            }, onError = {
                errorAction(it.errCode,it.error)
            })
        }

        val str1 = getString(R.string.home_text)
        val str2 = getString(R.string.home_text2)
        val str3 = getString(R.string.home_text3)
        val spannableString = SpannableString(str1+str2 +str3)
        spannableString.setSpan(ForegroundColorSpan(requireContext().getColor(R.color.text_333)), 0, str1.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(ForegroundColorSpan(requireContext().getColor(R.color.accept)), str1.length, str1.length+str2.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(ForegroundColorSpan(requireContext().getColor(R.color.text_333)), str1.length+str2.length, str1.length+str2.length+str3.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        mBinding.tvHomeFmToast.text = spannableString

        mViewModel.home()
        mBinding.srHomeFm.isRefreshing = true
        mBinding.srHomeFm.setOnRefreshListener {
            mViewModel.home()
        }


    }

    override fun showNetTimeOutDialog() {
        NetWorkDialog.showNetTimeOutDialog(requireContext())
    }

    override fun showLoading() {
        //mBinding.srHomeFm.isRefreshing = true
    }

    override fun dismissDialog() {
        mBinding.srHomeFm.isRefreshing = false
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fl_homeFm, fragment)
        fragmentTransaction.commit()
    }


}