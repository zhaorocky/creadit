package com.like.upper.pleasure.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.like.upper.pleasure.R
import com.like.upper.pleasure.ui.WebActivity


class PrivacyText : ConstraintLayout {

    constructor(context: Context) : super(context){
        initView(context,null)
    }

    lateinit var iv: ImageView

    @SuppressLint("Recycle", "CustomViewStyleable", "MissingInflatedId", "ResourceType")
    private fun initView(context: Context, attrs: AttributeSet?){
        
        val view : View = LayoutInflater.from(context).inflate(R.layout.privacy_text, this)
        view.setOnClickListener{
            context.startActivity(Intent(context,WebActivity::class.java))
        }

        attrs?.let {
            val ta : TypedArray = context.obtainStyledAttributes(it, R.styleable.privacy_text)

            if(!ta.getBoolean(R.styleable.viewTitle_showLeft,true)){
                iv.visibility = View.GONE
            }
            ta.recycle()
        }
    }


}
