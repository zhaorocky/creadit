package com.like.upper.pleasure.ui


import android.app.Activity
import android.content.Intent
import android.os.CountDownTimer
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ecuador.mvvm.base.net.observeData
import com.ecuador.mvvm.base.net.parseState
import com.ecuador.mvvm.base.ui.BaseVmActivity
import com.ecuador.mvvm.base.util.MessageDialogFragment
import com.like.upper.pleasure.R
import com.like.upper.pleasure.adapter.MoreInfoAdapter
import com.like.upper.pleasure.adapter.PlanAdapter
import com.like.upper.pleasure.adapter.TermAdapter
import com.like.upper.pleasure.databinding.ActivityLoanAmountBinding
import com.like.upper.pleasure.entity.MoreInfo
import com.like.upper.pleasure.entity.PlanItemInfo
import com.like.upper.pleasure.entity.ProductInfo
import com.like.upper.pleasure.entity.TryLoanResultInfo
import com.like.upper.pleasure.ui.vm.ChoseAmountViewModel
import com.like.upper.pleasure.util.NubmerUtils
import com.like.upper.pleasure.util.setOnSingleClickListener
import com.like.upper.pleasure.view.LoanDialogFragment
import com.like.upper.pleasure.view.NetWorkDialog
import java.math.BigDecimal

class LoanChoseAmountActivity : BaseVmActivity<ChoseAmountViewModel,ActivityLoanAmountBinding>() {

    override fun layoutId() = R.layout.activity_loan_amount

    private lateinit var adapter: ArrayAdapter<String>


    var choseAmount = ""
    var productId =""
    var detailId =""
    var haveChosed = true
    var loadDataStatus = false

    var haveSubmit  = false

    override fun initView() {

        mBinding.viewHeader.getLifrImage().setOnClickListener {
            finish()
        }

        mBinding.srl.setOnRefreshListener {
            mViewModel.getProductInfo()
        }

        mBinding.tvLoginSubmit.isEnabled = false
        mBinding.tvLoginSubmit.setBackgroundResource(R.drawable.bt_register)

        mBinding.lyChoseAmountChose.setOnClickListener {
            haveChosed =!haveChosed
            setBtStatus()
        }

        mBinding.tvLoginSubmit.setOnSingleClickListener {

            //showDialog(null)
            mViewModel.preSubmit(productId,detailId,choseAmount)
        }

        mBinding.rvChoseAmountTerm.layoutManager = GridLayoutManager(this,3)
        mBinding.rvChoseAmountMore.layoutManager = LinearLayoutManager(this)
        mBinding.rvChoseAmountPlan.layoutManager = LinearLayoutManager(this)

        mViewModel.getProductInfo()

        adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mBinding.spChoseAmount.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                mBinding.tvChoseAmountSp.text = items[position]
                choseAmount = items[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
        mBinding.spChoseAmount.adapter = adapter

        mViewModel.resultProductInfo.observeData(this){
            parseState(it,{
                setProductData(it);
            },{
                errorAction(it.errCode,it.error)
            })
        }

        mViewModel.tryLoanResult.observeData(this){
            parseState(it,{
                loadDataStatus = true
                setLoanData(it)
                setBtStatus()
            },{
                errorAction(it.errCode,it.error)
            })
        }

        mViewModel.preResult.observeData(this){
            parseState(it,{
                showDialog(it)
            },{
                errorAction(it.errCode,it.error)
            })
        }

        mBinding.tvChoseAmountSp.setOnClickListener {
            mBinding.spChoseAmount.performClick()
        }

        val str1 = getString(R.string.chose_aomoutn_bottom_text)
        var str2 = "10:00"
        var spannableString = SpannableString(str1+str2 )

        val countDownTimer = object : CountDownTimer(10*60*1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                val min = seconds / 60
                val sec = seconds % 60
                var minStr = "00"
                var secStr = "00"
                if(min<10){
                    minStr = "0"+min
                }
                if(sec<10){
                    secStr = "0"+sec
                }else{
                    secStr = sec.toString()
                }
                spannableString = SpannableString(str1+"$minStr:$secStr" )
                spannableString.setSpan(ForegroundColorSpan(getColor(R.color.text_999)), 0, str1.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                spannableString.setSpan(ForegroundColorSpan(getColor(R.color.purple)), str1.length, str1.length+str2.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

                mBinding.tvChoseAmountBottomText.setText(spannableString)

            }

            override fun onFinish() {
                spannableString = SpannableString(str1+"10:00" )
                spannableString.setSpan(ForegroundColorSpan(getColor(R.color.text_999)), 0, str1.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                spannableString.setSpan(ForegroundColorSpan(getColor(R.color.purple)), str1.length, str1.length+str2.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

                mBinding.tvChoseAmountBottomText.setText(spannableString)
            }
        }.start()
    }

    override fun showNetTimeOutDialog() {
        NetWorkDialog.showNetTimeOutDialog(this)
    }

    override fun onResume() {
        super.onResume()
        if(haveSubmit){
            setResultAct()
        }
    }

    private fun showDialog(it: TryLoanResultInfo?) {
        LoanDialogFragment()
            .setPositiveButton(it, View.OnClickListener {
                setResultAct()
            }).show(supportFragmentManager, "")

    }

    private fun setResultAct(){
        val intent = Intent(this,InfoBaseActivity::class.java)
        intent.putExtra("data",100)
        setResult(Activity.RESULT_OK,intent)
        finish()
    }

    private fun setBtStatus(){
        if(loadDataStatus && haveChosed){
            mBinding.tvLoginSubmit.isEnabled = true
            mBinding.tvLoginSubmit.setBackgroundResource(R.drawable.bt_register_can)
            mBinding.ivChoseAmountChose.setBackgroundResource(R.drawable.have_chose)

        }else{
            mBinding.tvLoginSubmit.isEnabled = false
            mBinding.tvLoginSubmit.setBackgroundResource(R.drawable.bt_register)
            mBinding.ivChoseAmountChose.setBackgroundResource(R.drawable.no_chose)
        }
    }

    private fun setLoanData(tryLoanResultInfo : TryLoanResultInfo?) {
        tryLoanResultInfo?.let {info ->
            mBinding.tvChoseAmountTex1.text = info.scientificAstronautCruelMetre
            mBinding.tvChoseAmountTex2.text = info.illAmericanTemperatureRareTurkey
            mBinding.tvChoseAmountTex3.text = info.frontTonguePrivateMineral
            mBinding.tvChoseAmountTex4.text = info.eachAnnouncementYouthMoralDirector
            mBinding.tvChoseAmountTex5.text = info.thankfulMetalCurtain
            //mBinding.tvChoseAmountTex6.text = info.illAmericanTemperatureRareTurkey
            mBinding.tvChoseAmountTex7.text = info.nativeLeftTrackHorribleLamb
            setMoreInfo(info.manFaxBattleDangerousKangaroo)
            setPlan(info.successfulTeenagerDoubleEvening)
        }
    }

    private fun setMoreInfo(moreInfoList: List<MoreInfo>?){
        if(!moreInfoList.isNullOrEmpty()){
            mBinding.rvChoseAmountMore.adapter = MoreInfoAdapter(this,moreInfoList)
        }
    }

    private fun setPlan(planItemInfoList : List<PlanItemInfo>?){
        if(!planItemInfoList.isNullOrEmpty()){
            mBinding.lyChoseAmountPan.visibility = View.VISIBLE
            mBinding.rvChoseAmountPlan.adapter = PlanAdapter(this,planItemInfoList)
        }else{
            mBinding.lyChoseAmountPan.visibility = View.GONE
        }
    }



    private fun setProductData(productInfo: ProductInfo?) {
        productInfo?.let {
            val productList = it.manyPowerfulShorts
            productId = productInfo.unfairHostessDevotionDelightedIreland.toString()
            if(!productList.isNullOrEmpty()){
                val productDetailInfo = productList[0]
                detailId = productDetailInfo.bestDifferentIllChocolate.toString()
                setSpinnerData(
                    productDetailInfo.deliciousHardFollowingSofa,
                    productDetailInfo.certainAncientFruitBasketball,
                    productDetailInfo.deadBuddhismLameAffairSeniorLuggage
                    )
                productDetailInfo.deadBuddhismLameAffairSeniorLuggage?.let {
                    choseAmount = it
                    mBinding.tvChoseAmountSp.text = NubmerUtils.numStringToStringNum1000(productDetailInfo.deadBuddhismLameAffairSeniorLuggage)

                    var term = 3
                    productDetailInfo.singleBritishGeographyMommy?.let {
                        term = 3
                    }
                    var termAdapter  = TermAdapter(this,term,productDetailInfo.extraStranger)
                    mBinding.rvChoseAmountTerm.adapter = termAdapter
                }
            }
            mViewModel.tryLoan(productId,detailId,choseAmount)
        }
    }

    var items = mutableListOf("9000","8000")
    private fun setSpinnerData(maxAmount : String?,ircAmount : String?,mixAmount : String?){


        try {
            if(maxAmount.isNullOrBlank()||ircAmount.isNullOrBlank()||mixAmount.isNullOrBlank()){
                return
            }
            items.clear()
            items.add(NubmerUtils.numStringToStringNum1000(maxAmount))

            val max = BigDecimal(maxAmount)
            val mix = BigDecimal(mixAmount)
            val irc = BigDecimal(ircAmount)
            if(max.toInt()>mix.toInt() && max.subtract(irc).toInt()>irc.toInt()){
                var currentValue = max.subtract(irc)
                while (currentValue.toInt()>mix.toInt()){
                    items.add(NubmerUtils.numStringToStringNum1000(currentValue.toString()))
                    currentValue = currentValue.subtract(irc)
                }
            }
            adapter.notifyDataSetChanged()
        }catch (e : Exception){
            e.printStackTrace()
        }



    }


}