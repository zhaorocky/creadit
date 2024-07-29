package com.ecuador.mvvm.base.util

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.ecuador.mvvm.R

class MessageDialogFragment : DialogFragment() {

    private var positiveButtonText: String = ""
    private var msg: String? = ""
    private var positiveButtonClickListener: View.OnClickListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_DialogFragment);
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_message, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.tv_message).text = msg
        view.findViewById<TextView>(R.id.tv_message).apply {
            setOnClickListener {
                positiveButtonClickListener?.onClick(it)
                dismiss()
            }
        }
        view.findViewById<TextView>(R.id.tv_message_click).setOnClickListener {
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        //宽度填满，高度自适应
        try {
            val dialogWindow = dialog?.window
            val lp = dialogWindow?.attributes
            dialogWindow?.setGravity(Gravity.TOP)
            lp?.width = ViewGroup.LayoutParams.MATCH_PARENT
            lp?.height = ViewGroup.LayoutParams.WRAP_CONTENT
            dialogWindow?.attributes = lp

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setPositiveButton(
        text: String,
        listener: View.OnClickListener? = null,
    ): MessageDialogFragment {
        positiveButtonText = text
        positiveButtonClickListener = listener
        return this
    }

    fun setDialogMessage(
        text: String?,
    ): MessageDialogFragment {
        msg = text
        return this
    }

}