package com.like.upper.pleasure.view

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.provider.Settings
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.kongzue.dialogx.dialogs.CustomDialog
import com.kongzue.dialogx.interfaces.OnBindView
import com.like.upper.pleasure.R
import com.like.upper.pleasure.util.setOnSingleClickListener

object NetWorkDialog {
    fun showNetTimeOutDialog(context:Context){
        CustomDialog.build()
            .setMaskColor(Color.parseColor("#80000000"))
            .setCustomView(object : OnBindView<CustomDialog>(R.layout.dialog_net_layout) {
                override fun onBind(dialog: CustomDialog?, v: View?) {
                val tvQutie=v?.findViewById<TextView>(R.id.tv_quite)
                val tvAgree=v?.findViewById<TextView>(R.id.tv_agree)
                val ivClose=v?.findViewById<ImageView>(R.id.iv_close)

                tvQutie?.setOnSingleClickListener {
                    dialog?.dismiss()
                }
                ivClose?.setOnSingleClickListener {
                    dialog?.dismiss()
                }
                tvAgree?.setOnClickListener {
                    context.startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
                    dialog?.dismiss()
                }
            }

        }).show()
    }
}