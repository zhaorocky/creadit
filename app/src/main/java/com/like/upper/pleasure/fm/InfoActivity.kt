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
import com.like.upper.pleasure.databinding.ActivityInfoBinding
import com.like.upper.pleasure.entity.BaseUserInfo
import com.like.upper.pleasure.entity.TypeChoseData
import com.like.upper.pleasure.ui.ChoseDataActivity
import com.like.upper.pleasure.ui.ChoseDateActivity
import com.like.upper.pleasure.fm.vm.InfoViewModel

class InfoActivity : BaseVmFragment<InfoViewModel,ActivityInfoBinding>() {

    override fun layoutId() = R.layout.activity_info

    var name1 : String = ""
    var name2 : String = ""
    var email : String = ""
    var date : String = ""
    var sex : String = "-1"

    override fun initView() {

        srl = mBinding.srl
        mBinding.tvLoginSubmit.isEnabled = false
        mBinding.tvLoginSubmit.setBackgroundResource(R.drawable.bt_register)

        mBinding.srl.setOnRefreshListener {
            mViewModel.getBaseInfo()
        }

        mBinding.etInfoActivityName1.addTextChangedListener {
            checkData(false)
        }
        mBinding.etInfoActivityName2.addTextChangedListener {
             checkData(false)
        }
        mBinding.etInfoActivityEmail.addTextChangedListener {
             checkData(false)
        }

        mBinding.viewLoginHeader.getLifrImage().setOnClickListener {
            activity?.finish()
        }

        mBinding.tvLoginSubmit.setOnClickListener {
            if (checkData(true)){
                mViewModel.saveBase(name1,name2,email,date,sex)
            }
        }

        mViewModel.loginResult.observeData(this) {
            parseState(it, {
                startAct()
            },{
                showErrorMessage(it.error)
            })
        }

        mBinding.lyInfoActivityDate.setOnClickListener{
            result.launch(Intent(requireContext(), ChoseDateActivity::class.java))
        }
        mBinding.lyInfoActivityGender.setOnClickListener{
            val intent = Intent(requireContext(), ChoseDataActivity::class.java)
            intent.putExtra("type",TypeChoseData.SEX.value)
            resultGender.launch(intent)
        }

        mViewModel.getBaseInfo()
        mViewModel.baseUserInfoResult.observeData(this){
            parseState(it,{data ->
                setUserData(data)
            },{
                showErrorMessage(it.error)
            })
        }
    }

    private fun setUserData(data : BaseUserInfo?){
        data?.let {
            mBinding.etInfoActivityName1.setText(it.tinyCrossroadsBellyTiresomeHat)
            mBinding.etInfoActivityName2.setText(it.chemicalEntranceMusicalChild)
            mBinding.etInfoActivityDate.setText(it.bitterHalfKangaroo)
            mBinding.tvInfoActivityGender.setText(it.standardLatestQuietRevision)
            mBinding.etInfoActivityEmail.setText(it.strongSpringCock)
            sex = it.hopelessZooHotMidnightTyphoon.toString()
            date = it.bitterHalfKangaroo.toString()
        }
        checkData(false)
    }

    private fun checkData(show : Boolean) : Boolean{
        mBinding.tvLoginSubmit.isEnabled = false
        mBinding.tvLoginSubmit.setBackgroundResource(R.drawable.bt_register)
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

        if(date.isBlank()){
            if(show) {
                showErrorMessage(getString(R.string.info1_chose_hint) + " " + getString(R.string.info1_Email))
            }
            return false
        }
        if(sex == "-1"){
            if(show) {
                showErrorMessage(getString(R.string.info1_chose_hint) + " " + getString(R.string.info1_sex))
            }
            return false
        }

        email = mBinding.etInfoActivityEmail.text.toString()
        if(email.isBlank()){
            if(show) {
                showErrorMessage(getString(R.string.info1_input_hint) + " " + getString(R.string.info1_Email))
            }
            return false
        }
        mBinding.tvLoginSubmit.isEnabled = true
        mBinding.tvLoginSubmit.setBackgroundResource(R.drawable.bt_register_can)
        return true
    }

    private fun startAct(){
        findNavController().navigate(R.id.infoContract)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }
        })
    }

    private val result = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if(result.resultCode == Activity.RESULT_OK){
            mBinding.etInfoActivityDate.setText(result.data?.getStringExtra("date"))
            if(result.data?.getStringExtra("date") == null){
                date = ""
            }else{
                date = result.data?.getStringExtra("date").toString()
            }
             checkData(false)
        }
    }

    private val resultGender = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if(result.resultCode == Activity.RESULT_OK){
            mBinding.tvInfoActivityGender.setText(result.data?.getStringExtra("value"))
            if(result.data?.getStringExtra("data") == null){
                sex = "-1"
            }else{
                sex = result.data?.getStringExtra("data").toString()
            }
             checkData(false)
        }
    }

}