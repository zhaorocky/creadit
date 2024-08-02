package com.like.upper.pleasure.ui

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.compose.ui.unit.dp
import androidx.core.view.isVisible
import com.ecuador.mvvm.base.net.observeData
import com.ecuador.mvvm.base.net.parseState
import com.ecuador.mvvm.base.ui.BaseVmActivity
import com.like.upper.pleasure.R
import com.like.upper.pleasure.adapter.MenuPagerAdapter
import com.like.upper.pleasure.databinding.ActivityMenuBinding
import com.like.upper.pleasure.ui.vm.MenuViewModel
import com.ecuador.mvvm.base.util.SharedPreferencesHelper
import com.kongzue.dialogx.DialogX
import com.kongzue.dialogx.dialogs.CustomDialog
import com.kongzue.dialogx.interfaces.OnBindView
import com.like.upper.pleasure.fm.InfoImageActivity
import com.like.upper.pleasure.util.AppUtils
import com.like.upper.pleasure.util.setOnSingleClickListener
import com.like.upper.pleasure.view.NetWorkDialog
import pub.devrel.easypermissions.EasyPermissions


class MenuActivity : BaseVmActivity<MenuViewModel, ActivityMenuBinding>() {

    lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    var permissions = arrayOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.READ_CONTACTS
    )
    val permissionsCode = 1001


    override fun layoutId() = R.layout.activity_menu
    override fun initView() {
        sharedPreferencesHelper = SharedPreferencesHelper.getInstance(this)
        mBinding.vpMenuAct.adapter = MenuPagerAdapter(this, 3)
        mBinding.vpMenuAct.isUserInputEnabled = false
        mBinding.vpMenuAct.offscreenPageLimit = 3

        mBinding.btnMenuAct.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.fragmentHome -> {
                    choseIndex = 1
                    mBinding.vpMenuAct.setCurrentItem(0, true)
                }

                R.id.fragmentOrder -> {
                    mBinding.vpMenuAct.setCurrentItem(1, true)
                    if (sharedPreferencesHelper.token?.isNotBlank() == true) {
                        mBinding.vpMenuAct.setCurrentItem(1, true)
                    } else {
                        toLogin()
                        choseIndex = 1
                        return@setOnItemSelectedListener false
                    }

                }

                R.id.fragmentMy -> {
                    mBinding.vpMenuAct.setCurrentItem(2, true)
                    if (sharedPreferencesHelper.token?.isNotBlank() == true) {
                        mBinding.vpMenuAct.setCurrentItem(2, true)
                    } else {
                        toLogin()
                        choseIndex = 2
                        return@setOnItemSelectedListener false
                    }
                }
            }
            return@setOnItemSelectedListener true

        }

        mViewModel.updateInfo.observeData(this) {
            parseState(it, { data ->
                data?.let {
                    if (data.arabicCloseIcelandNeitherMedium == "1") {
                        showUpdateDialog(true,data.ancientPronunciation)
                    }else if (data.arabicCloseIcelandNeitherMedium == "2"){
                        showUpdateDialog(true,data.ancientPronunciation)
                    }else{
                        checkPermission()
                    }
                }
            }, {
                errorAction(it.errCode, it.error)
            })
        }
    }

    var choseIndex = 0
    private fun toLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun onStart() {
        super.onStart()
        if (sharedPreferencesHelper.token?.isBlank() == true) {
            mBinding.vpMenuAct.setCurrentItem(choseIndex, true)
            mBinding.btnMenuAct.selectedItemId = R.id.fragmentHome
        }
    }

    override fun showNetTimeOutDialog() {
        NetWorkDialog.showNetTimeOutDialog(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        showUpdateDialog(false)
        //todo imei
        mViewModel.getUpdateInfo(
            AppUtils.getAppVersionName(this),
            AppUtils.getAppVersionCode(this),
            "1223sddfasd33"
        )

    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {

    }

    /**
     * 检查权限，
     */
    private fun checkPermission() {
        if (!EasyPermissions.hasPermissions(this, *permissions)) {
            requestPermissions(permissions, permissionsCode)
        }

    }

    /**
     * 是否是强制更新
     */
    private fun showUpdateDialog(isForce: Boolean,url:String?) {

        CustomDialog.build()
            .setMaskColor(getResources().getColor(R.color.half_transport))
            .setCustomView(object : OnBindView<CustomDialog>(R.layout.dialog_update_layout) {
                override fun onBind(dialog: CustomDialog?, v: View?) {

                    val tvQutie = v?.findViewById<TextView>(R.id.tv_quite)
                    val tvAgree = v?.findViewById<TextView>(R.id.tv_agree)
                    val ivClose = v?.findViewById<ImageView>(R.id.iv_close)
                    val vPosition = v?.findViewById<View>(R.id.v_position)
                    if (isForce) {
                        tvQutie?.isVisible = false
                        vPosition?.isVisible = false
                        ivClose?.setOnClickListener {
                            finish()
                        }
                    } else {
                        tvQutie?.isVisible = true
                        vPosition?.isVisible = true
                        ivClose?.setOnClickListener {
                            dialog?.dismiss()
                        }
                    }
                    tvQutie?.setOnSingleClickListener {
                        dialog?.dismiss()
                    }
                    tvAgree?.setOnClickListener {
                        val intent=Intent(this@MenuActivity,WebActivity::class.java)
                        intent.putExtra("loadUrl",url)
                        startActivity(intent)
                    }
                }
            }).show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }

}