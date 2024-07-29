package com.like.upper.pleasure.ui


import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.ecuador.mvvm.base.ui.BaseActivity
import com.like.upper.pleasure.R
import com.like.upper.pleasure.databinding.ActivityWebBinding


class WebActivity : BaseActivity<ActivityWebBinding>() {


    override fun layoutId(): Int = R.layout.activity_web

    override fun initView() {

        mBinding.viewWebActHeader.ivRightTitle.visibility = View.GONE
        mBinding.viewWebActHeader.setOnClickListener {
            finish()
        }
        setBaseWebSetting(mBinding.web.settings)
        mBinding.web.webViewClient = object : WebViewClient(){

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {

                view?.loadUrl(request?.url.toString())
                return true
            }
        }
        mBinding.web.loadUrl("https://www.baidu.com/")
    }

    fun setBaseWebSetting(webSetting: WebSettings){
        webSetting.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;

        webSetting.builtInZoomControls = true
        webSetting.blockNetworkImage = false
        webSetting.allowFileAccess = true
        webSetting.setGeolocationEnabled(true)
        webSetting.builtInZoomControls = true
        webSetting.layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
        webSetting.useWideViewPort = true
        webSetting.blockNetworkImage = false
        webSetting.javaScriptEnabled = true
        webSetting.useWideViewPort = true
        webSetting.setSupportZoom(false)
        webSetting.setSupportMultipleWindows(true)
        webSetting.domStorageEnabled = true
        webSetting.loadWithOverviewMode = true

    }
}