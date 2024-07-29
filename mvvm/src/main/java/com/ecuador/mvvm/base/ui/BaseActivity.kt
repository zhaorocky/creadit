package com.ecuador.mvvm.base.ui

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ecuador.mvvm.base.service.UiService
import com.ecuador.mvvm.base.util.ProgressDialogFragment
import com.ecuador.mvvm.base.util.SharedPreferencesHelper
import java.util.Locale


abstract class BaseActivity<DB : ViewDataBinding> : AppCompatActivity() , UiService {

    var initEventBus = false
    lateinit var mBinding: DB
    var srl : SwipeRefreshLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, layoutId())
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
        progressDialogFragment?.show(supportFragmentManager,"")
    }

    override fun dismissDialog() {
        progressDialogFragment?.dismiss()
        srl?.isRefreshing = false
    }


    override fun onDestroy() {
        super.onDestroy()

    }


    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        //super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun attachBaseContext(newBase: Context?) {
        val language = SharedPreferencesHelper.getInstance(this).myLuange
        if(language != null){
            super.attachBaseContext(attachBaseContext(newBase, language))
        }
    }

    fun attachBaseContext(context: Context?, language: String): Context? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (context != null) {
                return updateResources(context, language)
            }
            return context

        } else {
            context
        }
    }
    open fun updateResources(context: Context, language: String): Context? {
        val resources = context.resources
        val locale: Locale = Locale(language)
        val configuration: Configuration = resources.configuration
        configuration.setLocale(locale)
        configuration.setLocales(LocaleList(locale))
        return context.createConfigurationContext(configuration)
    }

}