package com.like.upper.pleasure.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.CountDownTimer
import android.view.View
import androidx.core.widget.addTextChangedListener
import com.ecuador.mvvm.base.net.observeData
import com.ecuador.mvvm.base.net.parseState
import com.ecuador.mvvm.base.ui.BaseVmActivity
import com.ecuador.mvvm.base.util.MessageDialogFragment
import com.like.upper.pleasure.R
import com.like.upper.pleasure.databinding.ActivityLoginBinding
import com.like.upper.pleasure.entity.LoginResult
import com.like.upper.pleasure.ui.vm.LoginViewModel
import com.ecuador.mvvm.base.util.SharedPreferencesHelper
import com.like.upper.pleasure.util.setOnSingleClickListener
import com.like.upper.pleasure.view.NetWorkDialog

class LoginActivity : BaseVmActivity<LoginViewModel,ActivityLoginBinding>() {

    override fun layoutId() = R.layout.activity_login

    var phone : String = ""
    var code : String = ""

    override fun initView() {

        mBinding.viewLoginHeader.getLifrImage().setOnClickListener {
            finish()
        }

        mBinding.etLoginPhone.addTextChangedListener {
            if(it != null){
                phone = it.toString()
            }else{
                phone = ""
            }
            setRegisterStatus()
        }

        mBinding.etLoginCode.addTextChangedListener {
            if(it != null){
                code = it.toString()
            }else{
                code = ""
            }
            setRegisterStatus()
        }

        mBinding.tvLoginSend.isEnabled = false
        mBinding.tvLoginSend.setOnSingleClickListener{
            startCount()
            mBinding.tvLoginSend.isEnabled = false
            mBinding.tvLoginSend.setColor(getColor(R.color.text_999))
            mViewModel.sendVerifyCode(mBinding.etLoginPhone.text.toString())
        }

        mBinding.tvLoginRegister.setOnSingleClickListener{
            mViewModel.login(mBinding.etLoginCode.text.toString(),mBinding.etLoginPhone.text.toString())
        }

        mViewModel.codeResult.observeData(this){
            parseState(it,{data ->
                mBinding.etLoginCode.setText(data?.digestBedroomStraitDusk)
                showDialog(data?.smsTemplate)
            },{

                errorAction(it.errCode,it.error)
            })
        }

        mViewModel.loginResult.observeData(this){
            parseState(it,{data ->
                setUserInfo(data)
            },{
                errorAction(it.errCode,it.error)
            })
        }
    }

    private fun setUserInfo(data: LoginResult?) {
        val  sharedPreferencesHelper = SharedPreferencesHelper.getInstance(this)
        sharedPreferencesHelper.loginStatus = true
        sharedPreferencesHelper.token = data?.emptyBasketBeauty
        sharedPreferencesHelper.newCustFlag = data?.possibleGreatZebra.toString()
        sharedPreferencesHelper.testCustFlag = data?.friendlyCuriousTreasure.toString()
        sharedPreferencesHelper.userId = data?.uglyReasonClearMeans
        sharedPreferencesHelper.phoneNo = mBinding.etLoginPhone.text.toString()

        if(data?.possibleGreatZebra == "1"){
            startActivity(Intent(this,InfoBaseActivity::class.java))
        }else{
            finish()
        }

    }


    private var countDownTimer: CountDownTimer? = null

    private fun startCount(){
        countDownTimer?.cancel()
        isTimer = true
        countDownTimer = object : CountDownTimer(60*1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                mBinding.tvLoginSend2.setText("$seconds")
            }

            override fun onFinish() {
                isTimer = false
                setRegisterStatus()
            }
        }.start()

    }

    fun stopCountdown() {
        countDownTimer?.cancel()
    }

    override fun showNetTimeOutDialog() {
        NetWorkDialog.showNetTimeOutDialog(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopCountdown()
    }

    var isTimer = false
    @SuppressLint("ResourceType")
    private fun setRegisterStatus(){
        if(phone.isBlank() || phone.length<9 || isTimer){
            mBinding.tvLoginSend.isEnabled = false
            mBinding.tvLoginRegister.isEnabled = false
            mBinding.tvLoginSend.setColor(getColor(R.color.text_999))
            mBinding.tvLoginRegister.setBackgroundResource(R.drawable.bt_register)
        }else{
            mBinding.tvLoginSend.isEnabled = true
            mBinding.tvLoginSend.setTextAndColor(getString(R.string.login_send2),getColor(R.color.purple_200))
        }

        if(code.isBlank() || code.length<4){
            mBinding.tvLoginRegister.isEnabled = false
            mBinding.tvLoginRegister.setBackgroundResource(R.drawable.bt_register)
        }else{
            mBinding.tvLoginRegister.isEnabled = true
            mBinding.tvLoginRegister.setBackgroundResource(R.drawable.bt_register_can)
        }

    }

    private fun showDialog(message : String?) {
        MessageDialogFragment()
            .setDialogMessage(message)
            .setPositiveButton(getString(R.string.login_message_clicksss), View.OnClickListener {

            }).show(supportFragmentManager, "")

    }


}