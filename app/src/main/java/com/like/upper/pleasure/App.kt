package com.like.upper.pleasure

import android.app.Application
import android.content.Context
import android.graphics.Color
import com.kongzue.dialogx.DialogX

class App : Application() {

    companion object {
        @Volatile
        private var instance: App? = null

        fun getInstance(): App =
            instance ?: synchronized(this) {
                instance ?: App().also { instance = it }
            }

        fun getAppContext(): Context = getInstance().applicationContext
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        DialogX.init(this)
    }

}