package com.like.upper.pleasure.view

import android.os.Bundle
import android.os.CountDownTimer
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.like.upper.pleasure.R
import com.like.upper.pleasure.adapter.MoreInfoAdapter
import com.like.upper.pleasure.adapter.PlanAdapter
import com.like.upper.pleasure.entity.MoreInfo
import com.like.upper.pleasure.entity.PlanItemInfo
import com.like.upper.pleasure.entity.TryLoanResultInfo

class LoanDialogFragment : DialogFragment() {

    private var positiveButtonText: String = ""
    private var msg: TryLoanResultInfo? = null
    private var positiveButtonClickListener: View.OnClickListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_DialogFragment1);
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_amount, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        isCancelable = false
        view.findViewById<TextView>(R.id.tv_dialog_text1).text = msg?.scientificAstronautCruelMetre
        view.findViewById<TextView>(R.id.tv_dialog_text2).text = msg?.scientificAstronautCruelMetre
        val rv = view.findViewById<RecyclerView>(R.id.rv_dialog)
        val tv = view.findViewById<TextView>(R.id.tv_close)
        val iv = view.findViewById<ImageView>(R.id.iv_close)

        iv.setOnClickListener {
            dismiss()
            positiveButtonClickListener?.onClick(it)
        }

        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = PlanAdapter(requireContext(),msg?.successfulTeenagerDoubleEvening)

        tv.text = "5s"
        val  countDownTimer = object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                tv.text = ("$seconds")

            }

            override fun onFinish() {
                dismiss()
                positiveButtonClickListener?.onClick(tv)
            }
        }.start()

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
        text: TryLoanResultInfo?,
        listener: View.OnClickListener? = null,
    ): LoanDialogFragment {
        msg = text
        positiveButtonClickListener = listener
        return this
    }

}