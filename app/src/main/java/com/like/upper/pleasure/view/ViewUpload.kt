package com.like.upper.pleasure.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.like.upper.pleasure.R
import com.luck.picture.lib.utils.ToastUtils


class ViewUpload : ConstraintLayout {

    constructor(context: Context) : super(context) {
        initView(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(context, attrs)
    }

    lateinit var ivTag: ImageView //中间的小图片
    lateinit var ivContent: ImageView//填充的图片
    lateinit var ivLoadStatus: ImageView//上方状态
    lateinit var tvTitle: TextView
    lateinit var progressBar: ProgressBar//进度条
    lateinit var flUpload: FrameLayout//蓝色背景

    @SuppressLint("Recycle", "CustomViewStyleable", "MissingInflatedId")
    private fun initView(context: Context, attrs: AttributeSet?) {
        val view: View = LayoutInflater.from(context).inflate(R.layout.view_upload, this)
        ivLoadStatus = view.findViewById(R.id.iv_upLoadStatus)
        ivContent = view.findViewById(R.id.iv_content)
        ivTag = view.findViewById(R.id.iv_tag)
        tvTitle = view.findViewById(R.id.tv_viewUp)
        progressBar = view.findViewById(R.id.progressBar)
        flUpload = view.findViewById(R.id.fl_upload)
        attrs?.let {
            val ta: TypedArray = context.obtainStyledAttributes(it, R.styleable.view_upload)
            tvTitle.text = ta.getString(R.styleable.view_upload_text_title)
            ivTag.setImageResource(ta.getResourceId(R.styleable.view_upload_image_center, -1))
            ta.recycle()
        }

//        rotateAnimator = ObjectAnimator.ofFloat(loading, "rotation", 0f, 360f)
    }

    fun getBasicLayout(): FrameLayout {
        return flUpload
    }

    fun getIvView(): ImageView {
        return ivContent
    }

    /**
     * imgUrl  如果有值代表设置过 如果没有值，代表还没有设置
     */
    fun setUploadSuccess(imgUrl: String?) {
        if (imgUrl?.isEmpty()==true){
            ivTag.isVisible=true
            ivLoadStatus.visibility = VISIBLE
        }else{
            ivTag.isVisible=false
            progressBar.isVisible = false
            ivContent.isVisible = true

            Glide.with(context)
                .load(imgUrl)
                .error(R.drawable.up_img_error)
                .into(ivContent)

            ivLoadStatus.visibility = VISIBLE
        }


    }

    //    lateinit var  rotateAnimator : ObjectAnimator
    fun setUploading() {

        progressBar.isVisible = true
        ivContent.isVisible = false
        ivLoadStatus.visibility = View.INVISIBLE

    }


}
