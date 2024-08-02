package com.like.upper.pleasure.fm


import android.app.Activity
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import com.ecuador.mvvm.base.net.observeData
import com.ecuador.mvvm.base.net.parseState
import com.ecuador.mvvm.base.ui.BaseVmFragment
import com.like.upper.pleasure.R
import com.like.upper.pleasure.databinding.FragmentMyBinding
import com.like.upper.pleasure.fm.vm.MineViewModel
import com.like.upper.pleasure.ui.LuangeActivity
import com.ecuador.mvvm.base.util.SharedPreferencesHelper
import com.like.upper.pleasure.entity.BaseUserInfo
import com.like.upper.pleasure.ui.MenuActivity
import com.like.upper.pleasure.view.NetWorkDialog


class MineFragment : BaseVmFragment<MineViewModel, FragmentMyBinding>() {

    override fun layoutId() = R.layout.fragment_my


    override fun initView() {
        sharedPreferencesHelper = SharedPreferencesHelper.getInstance(requireContext())
        srl = mBinding.srcMineFm
        mBinding.lyMyHomeSet.setOnClickListener {
            startSetActivity()
        }

        mViewModel.homeResult.observeData(this) {
            parseState(it, { productInfo ->

            },{
                errorAction(it.errCode,it.error)
            })
        }

        mViewModel.getBaseInfo()
        mViewModel.baseUserInfoResult.observeData(this){
            parseState(it,{data ->
                setUserData(data)
            },{
                errorAction(it.errCode,it.error)
            })
        }
    }

    private fun setUserData(data : BaseUserInfo?){
        data?.let {
            val name = it.tinyCrossroadsBellyTiresomeHat+it.followingFamiliarAccident
            if(name.isNotBlank()){
                sharedPreferencesHelper.userName = name
                mBinding.tvFmMyUsername.setText(name)
            }else{
                mBinding.tvFmMyUsername.text = getString(R.string.app_name)
            }

        }

    }
    lateinit var  sharedPreferencesHelper : SharedPreferencesHelper
    override fun showNetTimeOutDialog() {
        NetWorkDialog.showNetTimeOutDialog(requireContext())
    }

    override fun onStart() {
        super.onStart()
        mBinding.tvFmMyPhone.text = sharedPreferencesHelper.phoneNo
        val name = sharedPreferencesHelper.userName
        if(name?.isNotBlank() == true){
            mBinding.tvFmMyUsername.setText(name)
        }else{
            mBinding.tvFmMyUsername.text = getString(R.string.app_name)
        }
        //mBinding.ivMyHead.setImageResource(R.drawable.ic_launcher_foreground)
    }


    private fun startSetActivity() {
        result.launch(Intent(context, LuangeActivity::class.java))

    }

    private val result = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if(result.resultCode == Activity.RESULT_OK){
            if(result.data?.getBooleanExtra("data",false) == true){
                val intent = Intent(requireContext(), MenuActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
                activity?.finish()
            }
        }
    }



}