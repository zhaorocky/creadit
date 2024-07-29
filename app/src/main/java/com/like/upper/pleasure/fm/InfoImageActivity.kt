package com.like.upper.pleasure.fm

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
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
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class InfoImageActivity : BaseVmFragment<InfoImageModel,ActivityImageBinding>() {

    override fun layoutId() = R.layout.activity_image

    private val PICK_IMAGE = 100

    var imgUrl1 = ""
    var imgUrl2 = ""
    var card = ""
    var choseType = 1
    override fun initView() {
        srl = mBinding.srl


        mViewModel.baseUserInfoResult.observeData(this){
            parseState(it,{data ->
                setBaseData(data)
                mViewModel.getBaseImgInfo()
            },{
                showErrorMessage(it.error)
            })
        }

        mBinding.srl.setOnRefreshListener {
            mViewModel.getBaseInfo()
        }

        mBinding.etInfoImgCard.addTextChangedListener {
            checkData(false)
        }

        mViewModel.baseImgInfoResult.observeData(this){
            parseState(it,{data ->
                setImageData(data)
                checkData(false)
            },{
                showErrorMessage(it.error)
            })
        }
        mBinding.viewInfoImgHeader.getLifrImage().setOnClickListener {

        }

        mBinding.viewUpload1.lyDef.setOnClickListener {
            choseType =1
            chosePic(1)
        }

        mBinding.viewUpload1.iv.setOnClickListener {
            choseType =1
            chosePic(1)
        }

        mBinding.viewUpload2.lyDef.setOnClickListener {
            choseType = 2
            chosePic(2)
        }

        mBinding.viewUpload2.iv.setOnClickListener {
            choseType = 2
            chosePic(2)
        }

        mBinding.tvSubmit.setOnClickListener {
            mViewModel.saveCard(card)
        }

        mViewModel.uploadResult.observeData(this) {
            parseState(it, {
                if(choseType == 1){
                    imgUrl1 = "success"
                    mBinding.viewUpload1.setUploadSuccess()
                }else if(choseType == 2){
                    imgUrl2 = "success"
                    mBinding.viewUpload2.setUploadSuccess()
                }
                checkData(false)
            },{ ex->
                showErrorMessage(ex.error)
            })
        }

        mViewModel.loginResult.observeData(this){
            parseState(it,{
                findNavController().navigate(R.id.infoBank)
            },{ ex->
                showErrorMessage(ex.error)
            })
        }
        mViewModel.getBaseInfo()

    }

    private fun setBaseData(data: BaseUserInfo?) {
        data?.let {
            mBinding.etInfoImgCard.setText(it.youngPresentationDifficultFailureNeitherPhotograph)
            if(it.youngPresentationDifficultFailureNeitherPhotograph.isNullOrBlank()){
                card = ""
            }else{
                card = it.youngPresentationDifficultFailureNeitherPhotograph
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.infoContract)
            }
        })
    }

    private fun checkData(show : Boolean) : Boolean{
        mBinding.tvSubmit.isEnabled = false
        mBinding.tvSubmit.setBackgroundResource(R.drawable.bt_register)

        card = mBinding.etInfoImgCard.text.toString()

        if(card.isBlank()){
            if(show){
                showErrorMessage(getString(R.string.info1_input_hint))
            }
            return false
        }

        if(imgUrl1.isBlank()){
            if(show){
                showErrorMessage(getString(R.string.info1_input_hint)+" "+getString(R.string.info1_last_name))
            }
            return false
        }

        if(imgUrl2.isBlank()){
            if(show){
                showErrorMessage(getString(R.string.info1_input_hint)+" "+getString(R.string.info1_last_name))
            }
            return false
        }

        mBinding.tvSubmit.isEnabled = true
        mBinding.tvSubmit.setBackgroundResource(R.drawable.bt_register_can)
        return true
    }

    private fun setImageData(data: ImageInfo?) {
        data?.let {
            if(!it.sadSeniorDinnerGrandma.isNullOrBlank()){
                mBinding.viewUpload1.setUploadSuccess(it.sadSeniorDinnerGrandma)
                imgUrl1 = it.sadSeniorDinnerGrandma
            }else{
                imgUrl1 = ""
            }

            if(!it.challengingStraitFinalSchoolbag.isNullOrBlank()){
                mBinding.viewUpload2.setUploadSuccess(it.challengingStraitFinalSchoolbag)
                imgUrl2 = it.challengingStraitFinalSchoolbag
            }else{
                imgUrl2 = ""
            }
        }
        checkData(false)
    }

    private fun startAct(){
        findNavController().navigate(R.id.infoBank)
    }


    val permission =  arrayOf(
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    private fun chosePic(index : Int){
        if (requireContext().checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != android.content.pm.PackageManager.PERMISSION_GRANTED) {
            requestPermissions( permission, PICK_IMAGE)
            openGallery(index)
        } else {
            openGallery(index)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }

    private fun openGallery(index : Int) {
        try {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            if(index == 1){
                pickImageLauncher1.launch(gallery)
            }else if(index == 2){
                pickImageLauncher2.launch(gallery)
            }

        }catch (e : Exception){
            e.printStackTrace()
        }

    }

    private val pickImageLauncher1 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                getChosePic(uri,1)
            }
        }
    }

    private val pickImageLauncher2 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                getChosePic(uri,2)
            }
        }
    }

    fun compressImageToFile(uri: Uri, maxSize: Int): File? {
        var bitmap : Bitmap? = null
        if (android.os.Build.VERSION.SDK_INT >= 29) {
            val source = ImageDecoder.createSource(requireContext().contentResolver, uri)
            bitmap = ImageDecoder.decodeBitmap(source)
        } else {
            bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
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

    private fun getChosePic(uri : Uri,index : Int){
        if(index == 1){
            mBinding.viewUpload1.setUploading()
        }else if(index == 2){
            mBinding.viewUpload2.setUploading()
        }

        mViewModel.viewModelScope.launch {
            runCatching {
                compressImageToFile(uri,1024*100)
            }.onSuccess {
                if(index == 1){
                    mBinding.viewUpload1.setUpBit(BitmapFactory.decodeFile(it?.path))
                    mViewModel.uploadImg(it,"00")
                }else if(index == 2){
                    mBinding.viewUpload2.setUpBit(BitmapFactory.decodeFile(it?.path))
                    mViewModel.uploadImg(it,"01")
                }

            }.onFailure {
                it.printStackTrace()
            }
        }
    }

}