package com.like.upper.pleasure.ui

import android.app.Activity
import android.content.Intent
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import com.ecuador.mvvm.base.ui.BaseActivity
import com.like.upper.pleasure.R
import com.like.upper.pleasure.databinding.ActivityPrivacyBinding


class PrivacyActivity : BaseActivity<ActivityPrivacyBinding>() {
    override fun layoutId() = R.layout.activity_privacy

    override fun initView() {
        mBinding.tvAcceptPrivacyActivity.setOnClickListener{
            startLoginAct(true)
        }

        mBinding.tvCancelPrivacyActivity.setOnClickListener {
            startLoginAct(false)
        }

        val str1 = getString(R.string.toast_privacy)
        val str2 = getString(R.string.toast_privacy_red)
        val spannableString = SpannableString(str1+str2 )
        spannableString.setSpan(ForegroundColorSpan(getColor(R.color.text_999)), 0, str1.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(ForegroundColorSpan(getColor(R.color.accept)), str1.length, str1.length+str2.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        mBinding.tvPrivacyPrivacy.text = spannableString;
    }

    private fun startLoginAct(status : Boolean){
        val intent = Intent(this,StartActivity::class.java)
        intent.putExtra("agree",status)
        setResult(Activity.RESULT_OK,intent)
        finish()
    }




}