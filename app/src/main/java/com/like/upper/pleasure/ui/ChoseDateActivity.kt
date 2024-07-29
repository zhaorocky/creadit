package com.like.upper.pleasure.ui

import android.app.Activity
import android.content.Intent
import com.ecuador.mvvm.base.ui.BaseActivity
import com.like.upper.pleasure.R
import com.like.upper.pleasure.databinding.ActivityChoseDateBinding
import java.util.Calendar


class ChoseDateActivity : BaseActivity<ActivityChoseDateBinding>() {
    
    override fun layoutId() = R.layout.activity_chose_date

    override fun initView() {
        mBinding.tvChoseDateActOk.setOnClickListener {
            val intent = Intent()
            intent.putExtra("date","${mBinding.dpChoseDateAct.dayOfMonth}-${mBinding.dpChoseDateAct.month+1}-${mBinding.dpChoseDateAct.year}")
            setResult(Activity.RESULT_OK,intent)
            finish()
        }

        val maxDateCalendar = Calendar.getInstance()
        maxDateCalendar.set(2000, Calendar.DECEMBER, 31)
        mBinding.dpChoseDateAct.maxDate = maxDateCalendar.timeInMillis

        mBinding.tvChoseDateActCancel.setOnClickListener {
            finish()
        }
    }
}