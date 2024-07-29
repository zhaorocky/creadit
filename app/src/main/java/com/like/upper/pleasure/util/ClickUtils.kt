package com.like.upper.pleasure.util

import android.os.SystemClock
import android.view.View


    fun View.setOnSingleClickListener(interval: Long = 600, onClick: (View) -> Unit) {
        var lastClickTime = 0L

        setOnClickListener {
            val currentTime = SystemClock.elapsedRealtime()
            if (currentTime - lastClickTime > interval) {
                lastClickTime = currentTime
                onClick(it)
            }
        }
    }
