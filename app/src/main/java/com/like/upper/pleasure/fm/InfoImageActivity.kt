package com.like.upper.pleasure.fm

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.ecuador.mvvm.base.net.observeData
import com.ecuador.mvvm.base.net.parseState
import com.ecuador.mvvm.base.ui.BaseVmFragment
import com.like.upper.pleasure.R
import com.like.upper.pleasure.databinding.ActivityImageBinding
import com.like.upper.pleasure.entity.BaseUserInfo
import com.like.upper.pleasure.entity.ImageInfo
import com.like.upper.pleasure.fm.vm.InfoImageModel
import com.like.upper.pleasure.util.GlideEngine
import com.like.upper.pleasure.view.NetWorkDialog
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.engine.CompressFileEngine
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import top.zibin.luban.Luban
import top.zibin.luban.OnNewCompressListener
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


class InfoImageActivity : BaseVmFragment<InfoImageModel, ActivityImageBinding>(),
    EasyPermissions.PermissionCallbacks {

    override fun layoutId() = R.layout.activity_image

    companion object {
        const val PE_PICK_IMAGE = 100
    }

    var imgUrl1 = ""
    var imgUrl2 = ""
    var card = ""
    var choseType = 1
    override fun initView() {
        srl = mBinding.srl


        //身份证
        mViewModel.baseUserInfoResult.observeData(this) {
            parseState(it, { data ->
                setBaseData(data)
                mViewModel.getBaseImgInfo()
            }, {
                errorAction(it.errCode, it.error)
            })
        }

        mBinding.srl.setOnRefreshListener {
            mViewModel.getBaseInfo()

        }

        mBinding.etInfoImgCard.addTextChangedListener {
            checkData(false)
        }

        //请求数据接口返回的图片
        mViewModel.baseImgInfoResult.observeData(this) { it ->
            parseState(it, { data ->
                setImageData(data)
                checkData(false)
            }, {
                errorAction(it.errCode, it.error)
            })
        }
        mBinding.viewInfoImgHeader.getLifrImage().setOnClickListener {

        }

        mBinding.viewUpload1.getIvView().setOnClickListener {
            choseType = 1
            chosePic()
        }
        mBinding.viewUpload1.getBasicLayout().setOnClickListener {
            choseType = 1
            chosePic()
        }

        mBinding.viewUpload2.getBasicLayout().setOnClickListener {
            choseType = 2
            chosePic()
        }
        mBinding.viewUpload2.getIvView().setOnClickListener {
            choseType = 2
            chosePic()
        }


        mBinding.tvSubmit.setOnClickListener {
            mViewModel.saveCard(card)
        }

        mViewModel.uploadResult.observeData(this) {
            parseState(it, {
                Log.d("上传","成功===")
                if (choseType == 1) {
                    imgUrl1 = "success"
                    mBinding.viewUpload1.setUploadSuccess(it)
                } else if (choseType == 2) {
                    imgUrl2 = "success"
                    mBinding.viewUpload2.setUploadSuccess(it)
                }
                checkData(false)
            }, { ex ->
                errorAction(ex.errCode, ex.error)
            })
        }

        mViewModel.loginResult.observeData(this) {
            parseState(it, {
                startAct()
            }, { ex ->
                errorAction(ex.errCode, ex.error)
            })
        }
        mViewModel.getBaseInfo()

    }

    /**
     * 身份证处理 如果接口有值的话 就不让编辑了
     */
    private fun setBaseData(data: BaseUserInfo?) {
        data?.let {
            if (it.youngPresentationDifficultFailureNeitherPhotograph.isNullOrBlank()) {
                card = ""
                mBinding.etInfoImgCard.isEnabled = true
            } else {
                mBinding.etInfoImgCard.setText(it.youngPresentationDifficultFailureNeitherPhotograph)
                //有值的话就不要编辑了
                mBinding.etInfoImgCard.isEnabled = false
                card = it.youngPresentationDifficultFailureNeitherPhotograph
            }
        }
    }

    override fun showNetTimeOutDialog() {
        NetWorkDialog.showNetTimeOutDialog(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.infoContract)
                }
            })
    }

    //显示按钮逻辑
    private fun checkData(show: Boolean) {
        mBinding.tvSubmit.isEnabled = false
        mBinding.tvSubmit.setBackgroundResource(R.drawable.bt_register)

        card = mBinding.etInfoImgCard.text.toString()

        if (card.isBlank()) {
            if (show) {
                showErrorMessage(getString(R.string.info1_input_hint))
            }
            return
        }

        if (imgUrl1.isBlank()) {
            if (show) {
                showErrorMessage(getString(R.string.info1_input_hint) + " " + getString(R.string.info1_last_name))
            }
            return
        }

        if (imgUrl2.isBlank()) {
            if (show) {
                showErrorMessage(getString(R.string.info1_input_hint) + " " + getString(R.string.info1_last_name))
            }
            return
        }

        mBinding.tvSubmit.isEnabled = true
        mBinding.tvSubmit.setBackgroundResource(R.drawable.bt_register_can)
    }

    private fun setImageData(data: ImageInfo?) {
        data?.let {
            if (!it.sadSeniorDinnerGrandma.isNullOrBlank()) {
                //如果是接口过来的 说明之前已经设置好了 就不让后点击了
                mBinding.viewUpload1.getBasicLayout().isEnabled = false
                mBinding.viewUpload1.getIvView().isEnabled = false
                mBinding.viewUpload1.setUploadSuccess(it.sadSeniorDinnerGrandma)
                imgUrl1 = it.sadSeniorDinnerGrandma
            } else {
                imgUrl1 = ""
            }

            if (!it.challengingStraitFinalSchoolbag.isNullOrBlank()) {
                mBinding.viewUpload2.getBasicLayout().isEnabled = false
                mBinding.viewUpload2.getIvView().isEnabled = false
                mBinding.viewUpload2.setUploadSuccess(it.challengingStraitFinalSchoolbag)
                imgUrl2 = it.challengingStraitFinalSchoolbag
            } else {
                imgUrl2 = ""
            }
        }
        checkData(false)
    }

    private fun startAct() {
        findNavController().navigate(R.id.infoBank)
    }

    val permissions = arrayOf(
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.CAMERA
    )

    /**
     * 选择图片 i、
     * index 1:上面的图片  2：下面的图片
     */

    private fun chosePic() {
        if (EasyPermissions.hasPermissions(requireContext(), *permissions)) {
            pickImg()
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(
                this@InfoImageActivity,
                "Para poder ofrecerle un buen servicio, vaya a la configuración de su teléfono móvil para activar los permisos correspondientes",
                PE_PICK_IMAGE,
                *permissions
            );
        }

    }

    private fun pickImg() {
        PictureSelector.create(this)
            .openGallery(SelectMimeType.ofImage())
            .setImageEngine(GlideEngine.createGlideEngine())
            .setMaxSelectNum(1)
            .forResult(object : OnResultCallbackListener<LocalMedia?> {
                override fun onResult(result: ArrayList<LocalMedia?>) {
                   Log.d("result===","${result[0]?.path}")
                    val path=result.getOrNull(0)?.path
                    if (path?.isNotEmpty()==true){
                        uploadPic(path, index = choseType)
                    }

                }

                override fun onCancel() {
                }
            })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /**
     * 授权成功
     */
    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        pickImg()
    }

    /**
     * 授权拒绝
     */
    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        /**
        　　* 若是在权限弹窗中，用户勾选了'NEVER ASK AGAIN.'或者'不在提示'，且拒绝权限。
        　　* 这时候，需要跳转到设置界面去，让用户手动开启。
        　　*/

        if (EasyPermissions.somePermissionDenied(this, *perms.toTypedArray())) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }
    private fun compressImageToFile(path: String, maxSize: Int): File? {
        var bitmap : Bitmap? = null
        if (android.os.Build.VERSION.SDK_INT >= 29) {
            val source = ImageDecoder.createSource(requireContext().contentResolver, Uri.parse(path))
            bitmap = ImageDecoder.decodeBitmap(source)
        } else {
            bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, Uri.parse(path))
        }
        if(bitmap==null){
            return null
        }

        var stream = ByteArrayOutputStream()
        var quality = 100

        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream)
        while (stream.toByteArray().size > maxSize && quality > 0) {
            stream = ByteArrayOutputStream()
            quality -= 5
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream)
        }
        val file = File(activity?.cacheDir, "${System.currentTimeMillis()}pleasure.jpg")
        val fos = FileOutputStream(file)
        fos.write(stream.toByteArray())
        fos.flush()
        fos.close()
        return file
    }

    private fun uploadPic(path: String, index: Int) {
        mViewModel.viewModelScope.launch {
            runCatching {
                mBinding.viewUpload1.setUploading()
                compressImageToFile(path,1024*100)
            }.onSuccess {
                if(index == 1){
                    mBinding.viewUpload1.setUploadSuccess(it?.path)
                    mViewModel.uploadImg(it,"00")
                }else if(index == 2){
                    mBinding.viewUpload2.setUploadSuccess(it?.path)
                    mViewModel.uploadImg(it,"01")
                }

            }.onFailure {
                it.printStackTrace()
            }
        }
    }


}