package com.ecuador.mvvm.base.ui


import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.ecuador.mvvm.base.ui.vm.BaseViewModel
import java.lang.reflect.ParameterizedType

abstract class BaseVmLoaderListActivity<VM : BaseViewModel, VB : ViewDataBinding> : BaseActivity<VB>() {

    lateinit var mViewModel : VM
    override fun setVm() {
        mViewModel = ViewModelProvider(this)[getVmClazz(this)]

        initLoad()
    }

    @Suppress("UNCHECKED_CAST")
    fun <VM> getVmClazz(obj: Any): VM {
        return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as VM
    }

    private fun initLoad(){
        showLoadAdapter()
    }

    fun showErrorMessage(message: String){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }

    fun showLoadAdapter(){

    }

    fun showSuccessAdapter(){

    }
}