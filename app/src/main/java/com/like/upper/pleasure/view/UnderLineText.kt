package com.like.upper.pleasure.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.like.upper.pleasure.R


class UnderLineText : ConstraintLayout {

    constructor(context: Context) : super(context){
        initView(context,null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context,attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context,attrs)
    }

    lateinit var tvTitle: TextView
    lateinit var line: View

    @SuppressLint("Recycle", "CustomViewStyleable", "MissingInflatedId", "ResourceType")
    private fun initView(context: Context, attrs: AttributeSet?){
        
        val view : View = LayoutInflater.from(context).inflate(R.layout.underline_text, this)
        tvTitle = view.findViewById(R.id.tv_underLine_text)
        line = view.findViewById(R.id.line_underText)
        attrs?.let {
            val ta : TypedArray = context.obtainStyledAttributes(it,R.styleable.underLine)
            tvTitle.text = ta.getString(R.styleable.underLine_text_underLine)
            val color = ta.getColor(R.styleable.underLine_underLine_color, R.color.text_333)
            line.setBackgroundColor(color)
            tvTitle.setTextColor(color)
            ta.recycle()
        }
    }

    fun setText(str: String){
        tvTitle.text = str
    }

    fun setColor(color : Int){
        tvTitle.setTextColor(color)
        line.setBackgroundColor(color)
    }

    fun setTextAndColor(str: String, color: Int){
        setText(str)
        setColor(color)
    }


}
