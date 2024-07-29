package com.like.upper.pleasure.ui


import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.like.upper.pleasure.R
import com.ecuador.mvvm.base.util.SharedPreferencesHelper
import java.util.Timer
import java.util.TimerTask


class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_start)


    }

    override fun onStart() {
        super.onStart()
        result = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result ->
            if(result.resultCode == RESULT_OK){
                val status = result.data?.getBooleanExtra("agree",false)
                if(true != status ){
                    finish()
                }else{
                    SharedPreferencesHelper.getInstance(this).haveAccept = true
                    startMenuActivity()
                }
            }
        }
        val  countDownTimer = object : CountDownTimer(1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                startAct()
            }
        }.start()
    }



    private fun startAct(){
        val sharedPreferences = SharedPreferencesHelper.getInstance(this)
        if(sharedPreferences.haveAccept){
            startMenuActivity()
        }else{
            result.launch(Intent(this,PrivacyActivity::class.java))
        }

    }


    lateinit var result : ActivityResultLauncher<Intent>


    private fun startMenuActivity(){
        startActivity(Intent(this,MenuActivity::class.java))
        finish()
    }

}