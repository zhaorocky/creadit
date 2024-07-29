package com.like.upper.pleasure.view

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.like.upper.pleasure.R


class ViewUpload : ConstraintLayout {

    constructor(context: Context) : super(context){
        initView(context,null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context,attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context,attrs)
    }

    lateinit var lyDef: LinearLayout
    lateinit var iv: ImageView
    lateinit var ivStatus: ImageView
    lateinit var ivLoadStatus: ImageView
    lateinit var loading: ImageView
    lateinit var tvTitle: TextView

    @SuppressLint("Recycle", "CustomViewStyleable", "MissingInflatedId")
    private fun initView(context: Context, attrs: AttributeSet?){
        
        val view : View = LayoutInflater.from(context).inflate(R.layout.view_upload, this)
        lyDef = view.findViewById(R.id.ly_viewUp)
        iv = view.findViewById(R.id.iv_viewUp)
        ivLoadStatus = view.findViewById(R.id.iv_upLoadStatus)
        ivStatus = view.findViewById(R.id.iv_viewUp_status)
        loading = view.findViewById(R.id.iv_viewUp_loading)
        tvTitle = view.findViewById(R.id.tv_viewUp)

        attrs?.let {
            val ta : TypedArray = context.obtainStyledAttributes(it,R.styleable.view_upload)
            tvTitle.text = ta.getString(R.styleable.view_upload_text_title)
            ivStatus.setImageResource(ta.getResourceId(R.styleable.view_upload_image_center,-1))
            ta.recycle()
        }

        rotateAnimator = ObjectAnimator.ofFloat(loading, "rotation", 0f, 360f)
    }

    fun getDef() : LinearLayout{
        return lyDef
    }

    fun getIvView() : ImageView{
        return iv
    }

    fun setUploadSuccess(imgUrl : String?){
        lyDef.visibility = View.INVISIBLE
        loading.visibility = View.INVISIBLE
        iv.visibility = View.VISIBLE

        Glide.with(context)
            .load(imgUrl)
            .error(R.drawable.up_img_error)
            .into(iv)

        ivLoadStatus.visibility = VISIBLE
    }

    fun setUploadSuccess(){
        ivLoadStatus.visibility = VISIBLE
    }

    fun setUpBit(bitmap : Bitmap?){
        lyDef.visibility = View.INVISIBLE
        loading.visibility = View.INVISIBLE
        iv.visibility = View.VISIBLE
        setUploadingDissmiss()
        iv.setImageBitmap(bitmap)
    }

    lateinit var  rotateAnimator : ObjectAnimator
    fun setUploading(){

        lyDef.visibility = View.INVISIBLE
        loading.visibility = View.VISIBLE
        iv.visibility = View.INVISIBLE
        ivLoadStatus.visibility = View.INVISIBLE

        rotateAnimator.duration = 1000
        rotateAnimator.repeatCount = ObjectAnimator.INFINITE
        rotateAnimator.repeatMode = ObjectAnimator.RESTART

        rotateAnimator.start()
    }

    fun setUploadingDissmiss(){
        rotateAnimator.cancel()
    }

    fun setDef(){
        lyDef.visibility = View.VISIBLE
        loading.visibility = View.INVISIBLE
        iv.visibility = View.INVISIBLE
        ivLoadStatus.visibility = View.INVISIBLE
    }


}
