package com.like.upper.pleasure.ui


import android.app.Activity
import android.content.Intent
import com.ecuador.mvvm.base.ui.BaseActivity
import com.ecuador.mvvm.base.util.SharedPreferencesHelper
import com.like.upper.pleasure.R
import com.like.upper.pleasure.databinding.ActivityLuangeBinding


class LuangeActivity : BaseActivity<ActivityLuangeBinding>() {

    val sharedPreferencesHelper = SharedPreferencesHelper.getInstance(this)

    override fun layoutId() = R.layout.activity_luange

    override fun initView() {

        mBinding.viewLanguageHeader.setOnClickListener {
            finish()
        }

        mBinding.tvSetLanguageOut.setOnClickListener {

            logout();
        }

        mBinding.lyEnglish.setOnClickListener{
            saveL("en")
            setStatusLuange("en")
            val intent = Intent(this,LoginActivity::class.java)
            intent.putExtra("data",true)
            setResult( Activity.RESULT_OK,intent)
            finish()
        }

        mBinding.lySpanish.setOnClickListener{
            saveL("es")
            setStatusLuange("es")
            val intent = Intent(this,LoginActivity::class.java)
            intent.putExtra("data",true)
            setResult( Activity.RESULT_OK,intent)
            finish()
        }

        setStatusLuange(sharedPreferencesHelper.myLuange)
    }

    fun setStatusLuange(luange : String?){
       if("en".equals(luange)){
           mBinding.ivEnSwitch.setImageResource(R.drawable.switch_on)
           mBinding.ivEsSwitch.setImageResource(R.drawable.switch_off)
       }else{
           mBinding.ivEnSwitch.setImageResource(R.drawable.switch_off)
           mBinding.ivEsSwitch.setImageResource(R.drawable.switch_on)
       }
    }

    private fun logout(){
        sharedPreferencesHelper.token = ""
        sharedPreferencesHelper.userId = ""
        sharedPreferencesHelper.phoneNo = ""
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
    }

    private fun saveL(l : String){
        sharedPreferencesHelper.myLuange = l
    }


}