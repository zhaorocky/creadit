package com.like.upper.pleasure.ui


import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import com.ecuador.mvvm.base.ui.BaseActivity
import com.like.upper.pleasure.R
import com.like.upper.pleasure.databinding.ActivityWebBinding


class WebActivity : BaseActivity<ActivityWebBinding>() {
    var loadUrl:String?=null  //传进来的url

    override fun layoutId(): Int = R.layout.activity_web

    override fun initView() {

        loadUrl=intent.getStringExtra("loadUrl")
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

            override fun onReceivedError(
                view: WebView?,
                errorCode: Int,
                description: String?,
                failingUrl: String?
            ) {
                super.onReceivedError(view, errorCode, description, failingUrl)
                mBinding.progressBar.isVisible=false
                mBinding.web.isVisible=false
                mBinding.layoutError.root.isVisible=true
                mBinding.layoutError.tvRetry.setOnClickListener {
                    view?.reload()
                }
            }
        }

        mBinding.web.webChromeClient=object :WebChromeClient(){
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                mBinding.layoutError.root.isVisible=false
                mBinding.progressBar.progress=newProgress
                if (newProgress==100){
                    mBinding.progressBar.isVisible=false
                    mBinding.web.isVisible=true
                }else{
                    mBinding.progressBar.isVisible=true
                    mBinding.web.isVisible=false
                }
            }

        }

        mBinding.web.loadUrl(loadUrl.orEmpty())
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