package com.like.upper.pleasure.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.like.upper.pleasure.R
import com.like.upper.pleasure.ui.WebActivity
import com.like.upper.pleasure.view.onclick.OnCustomerViewClick


class ViewTitle : ConstraintLayout {

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
    lateinit var ivLeftTitle: ImageView
    lateinit var ivRightTitle: ImageView

    @SuppressLint("Recycle", "CustomViewStyleable")
    private fun initView(context: Context, attrs: AttributeSet?){
        
        val view : View = LayoutInflater.from(context).inflate(R.layout.view_title, this)

        tvTitle = view.findViewById(R.id.tv_viewTitle_title)
        ivLeftTitle = view.findViewById(R.id.iv_viewTitle_left)
        ivRightTitle = view.findViewById(R.id.iv_viewTitle_right)
        ivRightTitle.setOnClickListener {
            context.startActivity(Intent(context,WebActivity::class.java))
        }

        attrs?.let {
            val ta : TypedArray = context.obtainStyledAttributes(it,R.styleable.viewTitle)

            tvTitle.text = ta.getString(R.styleable.viewTitle_text_title_view)
            ivLeftTitle.setImageResource(ta.getResourceId(R.styleable.viewTitle_left_title_image,-1))
            ivRightTitle.setImageResource(ta.getResourceId(R.styleable.viewTitle_right_title_image,-1))

            if(!ta.getBoolean(R.styleable.viewTitle_showLeft,true)){
                ivLeftTitle.visibility = View.GONE
            }

            if(!ta.getBoolean(R.styleable.viewTitle_showRight,true)){
                ivRightTitle.visibility = View.GONE
            }
            ta.recycle()
        }
    }



    fun getLifrImage() : ImageView{
        return ivLeftTitle
    }

    fun setViewTitleText(title : String){
        tvTitle.text = title
    }

    fun setOnLeftImageClick(onClick : OnCustomerViewClick){
        ivLeftTitle.setOnClickListener {
            onClick.onClick()
        }
    }

    fun setOnRightImageClick(onClick : OnCustomerViewClick){
        ivRightTitle.setOnClickListener {
            onClick.onClick()
        }
    }

    fun setLeftImageShow(status : Int){
        ivLeftTitle.visibility = status
    }

    fun setRightImageShow(status : Int){
        ivRightTitle.visibility = status
    }

}
