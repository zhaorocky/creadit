package com.like.upper.pleasure.fm

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.ecuador.mvvm.base.net.observeData
import com.ecuador.mvvm.base.net.parseState
import com.ecuador.mvvm.base.ui.BaseVmActivity
import com.ecuador.mvvm.base.ui.BaseVmFragment
import com.like.upper.pleasure.R
import com.like.upper.pleasure.databinding.ActivityInfoLoan1Binding
import com.like.upper.pleasure.entity.BankAcountInfoItem
import com.like.upper.pleasure.entity.TypeChoseData
import com.like.upper.pleasure.ui.ChoseDataActivity
import com.like.upper.pleasure.ui.LoanChoseAmountActivity
import com.like.upper.pleasure.ui.vm.InfoLoanViewModel

class InfoLoan1Activity : BaseVmFragment<InfoLoanViewModel,ActivityInfoLoan1Binding>() {

    override fun layoutId() = R.layout.activity_info_loan1


    var bankNum : String = ""
    var confirmBankNum : String = ""

    var type1 : String = "-1"
    var type2 : String = "-1"
    var type3 : String = "-1"
    var type4 : String = "-1"

    var checkBankName = true

    override fun initView() {

        mBinding.viewHeader.getLifrImage().setOnClickListener {
            findNavController().navigate(R.id.infoImage)
        }

        mBinding.srl.setOnRefreshListener {
            mViewModel.getBankInfo()
        }

        mBinding.tvSubmit.setOnClickListener {
            if(checkData()){
                mViewModel.saveBankInfo(type1,type2,type3,type4,bankNum)
            }
        }

        mBinding.etInfoActivityConfirm.addTextChangedListener {
            checkData()
        }
        mBinding.etInfoActivityBankAccount.addTextChangedListener {
            checkData()
        }

        mBinding.lyInfoActivityLoan.setOnClickListener {
            val intent = Intent(requireContext(), ChoseDataActivity::class.java)
            intent.putExtra("type", TypeChoseData.COLLECTIONTYPE.value)
            typeResult1.launch(intent)
        }

        mBinding.lyInfoActivityType.setOnClickListener {
            val intent = Intent(requireContext(), ChoseDataActivity::class.java)
            intent.putExtra("type",TypeChoseData.BANKLIST.value)
            typeResult2.launch(intent)
        }

        mBinding.lyInfoActivityName.setOnClickListener {
            val intent = Intent(requireContext(), ChoseDataActivity::class.java)
            intent.putExtra("type",TypeChoseData.BANKNAME.value)
            typeResult3.launch(intent)
        }

        mViewModel.getBankInfo()
        mViewModel.bankResult.observeData(this){
            parseState(it,{data ->
                data?.let {
                    setUserData(data)
                }
            },{
                showErrorMessage(it.error)
            })
        }

        mViewModel.saveResult.observeData(this){
            parseState(it,{data ->
                startAct()
            },{
                showErrorMessage(it.error)
            })
        }
    }
    override fun showLoading() {
        mBinding.srl.isRefreshing = true
    }

    override fun dismissDialog() {
        mBinding.srl.isRefreshing = false
    }

    private fun startAct(){
        //startActivity(Intent(requireContext(), LoanChoseAmountActivity::class.java))
        //activity?.finish()
        okResult.launch(Intent(requireContext(), LoanChoseAmountActivity::class.java))
    }

    private fun setUserData(data: MutableList<BankAcountInfoItem>) {
        if(data.size==0){
            return
        }
        data[0].let {
            type1 = it.uncertainMealPillowTechnique
            type2 = it.moralReadingSunnySmog
            type3 = it.maleCuriousMiddle
            if( "2" == type1){
                checkBankName = false
                mBinding.lyActivityInfo1Type2.visibility = View.GONE
                mBinding.lyActivityInfo1Type3.visibility = View.GONE
                mBinding.tvInfoLoanAccount.text = getString(R.string.info_loan2_2)
                mBinding.tvInfoLoanAccount.text = getString(R.string.info_loan2_3)
                mBinding.etInfoActivityBankAccount.setText(it.briefMirrorHarbourPath)
                mBinding.etInfoActivityConfirm.setText(it.briefMirrorHarbourPath)
            }else{
                checkBankName = true
                mBinding.lyActivityInfo1Type2.visibility = View.VISIBLE
                mBinding.lyActivityInfo1Type3.visibility = View.VISIBLE
                mBinding.tvInfoLoanAccount.text = getString(R.string.info_loan1_bank_acoount)
                mBinding.tvInfoLoanAccount.text = getString(R.string.info_loan1_confirm_account)
                mBinding.etInfoActivityBankAccount.setText(it.readySeveralBasicRat)
                mBinding.etInfoActivityConfirm.setText(it.readySeveralBasicRat)
            }
            mBinding.tvInfoActivityLoan.text = it.popularPrayerFolkTiresomeEffort
            mBinding.tvInfoActivityType.text = it.possibleGreeceBenchCandy
            mBinding.tvInfoActivityName.text = it.bravePalaceAttractiveRecorder


        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.infoImage)
            }
        })
    }

    private fun checkData() : Boolean{

        mBinding.tvSubmit.isEnabled = false
        mBinding.tvSubmit.setBackgroundResource(R.drawable.bt_register)
        if(type1.isBlank()){
            return false
        }

        if(checkBankName){
            if(type2.isBlank()){
                return false
            }
            if(type3.isBlank()){
                return false
            }
        }

        bankNum = mBinding.etInfoActivityBankAccount.text.toString()
        if(bankNum.isBlank()){
            return false
        }

        confirmBankNum = mBinding.etInfoActivityConfirm.text.toString()
        if(confirmBankNum.isBlank()){
            return false
        }

        if(!bankNum.equals(confirmBankNum)){
            //showErrorMessage(getString(R.string.info_loan1_confirm_account))
            return false
        }
        mBinding.tvSubmit.isEnabled = true
        mBinding.tvSubmit.setBackgroundResource(R.drawable.bt_register_can)
        return true
    }

    private val typeResult1 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if(result.resultCode == Activity.RESULT_OK){
            mBinding.tvInfoActivityLoan.setText(result.data?.getStringExtra("value"))
            result.data?.getStringExtra("data")?.let {
                type1 = it
                if("2".equals(it)){
                    checkBankName = false
                    mBinding.lyActivityInfo1Type2.visibility = View.GONE
                    mBinding.lyActivityInfo1Type3.visibility = View.GONE
                    mBinding.tvInfoLoanAccount.text = getString(R.string.info_loan2_2)
                    mBinding.tvInfoLoanAccount.text = getString(R.string.info_loan2_3)
                }else{
                    checkBankName = true
                    mBinding.lyActivityInfo1Type2.visibility = View.VISIBLE
                    mBinding.lyActivityInfo1Type3.visibility = View.VISIBLE
                    mBinding.tvInfoLoanAccount.text = getString(R.string.info_loan1_bank_acoount)
                    mBinding.tvInfoLoanAccount.text = getString(R.string.info_loan1_confirm_account)
                }
            }
            checkData()
        }
    }

    private val typeResult2 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if(result.resultCode == Activity.RESULT_OK){
            result.data?.getStringExtra("value")?.let {
                mBinding.tvInfoActivityType.setText(it)
            }
            type2 = result.data?.getStringExtra("data").toString()
            checkData()
        }
    }

    private val typeResult3 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if(result.resultCode == Activity.RESULT_OK){
            result.data?.getStringExtra("value")?.let {
                mBinding.tvInfoActivityName.setText(it)
            }
            type3 = result.data?.getStringExtra("data").toString()
            type4 = result.data?.getStringExtra("value").toString()
            checkData()
        }
    }

    private val okResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if(result.resultCode == Activity.RESULT_OK){
            if(result.data?.getIntExtra("data",-1) == 100){
                activity?.finish()
            }

        }
    }



}