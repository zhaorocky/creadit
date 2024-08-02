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
import com.ecuador.mvvm.base.ui.BaseVmFragment
import com.like.upper.pleasure.R
import com.like.upper.pleasure.databinding.ActivityContactBinding
import com.like.upper.pleasure.entity.BaseUserInfo
import com.like.upper.pleasure.entity.TypeChoseData
import com.like.upper.pleasure.fm.vm.InfoContractModel
import com.like.upper.pleasure.ui.ChoseDataActivity
import com.like.upper.pleasure.fm.vm.InfoViewModel
import com.like.upper.pleasure.view.NetWorkDialog

class InfoContactActivity : BaseVmFragment<InfoContractModel,ActivityContactBinding>() {

    override fun layoutId() = R.layout.activity_contact

    var name1 : String = ""
    var name2 : String = ""
    var phoneNum1 : String = ""
    var phoneNum2 : String = ""
    var relation1 : String = "-1"
    var relation2 : String = "-1"

    override fun initView() {
        srl = mBinding.srl
        mBinding.viewLoginHeader.getLifrImage().setOnClickListener {
            findNavController().navigate(R.id.infoBaseINfo)
        }

        mBinding.srl.setOnRefreshListener {
            mViewModel.getBaseInfo()
        }

        mBinding.tvRegister.isEnabled = false
        mBinding.tvRegister.setBackgroundResource(R.drawable.bt_register)

        mBinding.etInfoActivityName1.addTextChangedListener {
            checkData(false)
        }
        mBinding.etInfoActivityName2.addTextChangedListener {
            checkData(false)
        }
        mBinding.etInfoActivityNum1.addTextChangedListener {
            checkData(false)
        }
        mBinding.etInfoActivityNum2.addTextChangedListener {
            checkData(false)
        }

        mBinding.tvRegister.setOnClickListener {
            mViewModel.saveContract(name1,name2,phoneNum1,phoneNum2,relation1,relation2)
        }

        mBinding.lyInfoActConRelation.setOnClickListener {
            val intent = Intent(requireContext(), ChoseDataActivity::class.java)
            intent.putExtra("type", TypeChoseData.RELATIONG.value)
            resultRelation1.launch(intent)
        }

        mBinding.lyInfoActConRelation2.setOnClickListener {
            val intent = Intent(requireContext(), ChoseDataActivity::class.java)
            intent.putExtra("type", TypeChoseData.RELATIONG.value)
            resultRelation2.launch(intent)
        }

        mViewModel.getBaseInfo()
        mViewModel.baseUserInfoResult.observeData(this){
            parseState(it,{data ->
                setUserData(data)
            },{
                errorAction(it.errCode,it.error)
            })
        }

        mViewModel.loginResult.observeData(this){
            parseState(it,{data ->
                startAct()
            },{
                errorAction(it.errCode,it.error)
            })
        }
    }

    override fun showNetTimeOutDialog() {
        NetWorkDialog.showNetTimeOutDialog(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.infoBaseINfo)
            }
        })
    }
    override fun showLoading() {
        mBinding.srl.isRefreshing = true
    }

    override fun dismissDialog() {
        mBinding.srl.isRefreshing = false
    }

    private fun startAct(){
        findNavController().navigate(R.id.infoImage)
    }

    private fun setUserData(data : BaseUserInfo?){
        data?.let {
            mBinding.etInfoActivityNum1.setText(it.lameVaseSuchDeadlineMarket)
            mBinding.etInfoActivityNum2.setText(it.juicySureAuthorBusiness)
            mBinding.etInfoActivityName1.setText(it.maleMostTheme)
            mBinding.etInfoActivityName2.setText(it.merryAidsServant)
            mBinding.tvInfoActConRelation1.setText(it.cruelSealCottonReligionMetalSomeone)
            mBinding.tvInfoActConRelation2.setText(it.looseChickTooth)
            it.seniorPedestrianElectricalRain?.let {
                relation1 = it
            }
            it.followingFamiliarAccident?.let {
                relation2 = it
            }
        }
        checkData(false)
    }

    private fun checkData(show : Boolean) : Boolean{
        mBinding.tvRegister.isEnabled = false
        mBinding.tvRegister.setBackgroundResource(R.drawable.bt_register)
        name1 = mBinding.etInfoActivityName1.text.toString()
        if(name1.isBlank()){
            if(show){
                showErrorMessage(getString(R.string.info1_input_hint)+" "+getString(R.string.info1_last_name))
            }
            return false
        }
        name2 = mBinding.etInfoActivityName2.text.toString()
        if(name2.isBlank()){
            if(show) {
                showErrorMessage(getString(R.string.info1_input_hint) + " " + getString(R.string.info1_names))
            }
            return false
        }

        phoneNum1 = mBinding.etInfoActivityNum1.text.toString()
        if(phoneNum1.isBlank()){
            if(show){
                showErrorMessage(getString(R.string.info1_input_hint)+" "+getString(R.string.info2_telephone))
            }
            return false
        }
        phoneNum2 = mBinding.etInfoActivityNum2.text.toString()
        if(phoneNum2.isBlank()){
            if(show) {
                showErrorMessage(getString(R.string.info1_input_hint) + " " + getString(R.string.info2_telephone))
            }
            return false
        }

        if(relation1 == "-1"){
            if(show) {
                showErrorMessage(getString(R.string.info1_chose_hint) + " " + getString(R.string.info2_relationship))
            }
            return false
        }

        if(relation2 == "-1"){
            if(show) {
                showErrorMessage(getString(R.string.info1_chose_hint) + " " + getString(R.string.info2_relationship))
            }
            return false
        }

        mBinding.tvRegister.isEnabled = true
        mBinding.tvRegister.setBackgroundResource(R.drawable.bt_register_can)
        return true
    }

    private val resultRelation1 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if(result.resultCode == Activity.RESULT_OK){
            mBinding.tvInfoActConRelation1.setText(result.data?.getStringExtra("value"))
            if(result.data?.getStringExtra("data") == null){
                relation1 = "-1"
            }else{
                relation1 = result.data?.getStringExtra("data").toString()
            }
        }
        checkData(false)
    }

    private val resultRelation2 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if(result.resultCode == Activity.RESULT_OK){
            mBinding.tvInfoActConRelation2.setText(result.data?.getStringExtra("value"))
            if(result.data?.getStringExtra("data") == null){
                relation2 = "-1"
            }else{
                relation2 = result.data?.getStringExtra("data").toString()
            }
            checkData(false)
        }
    }

}