package com.ecuador.mvvm.base.ui

import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.ecuador.mvvm.base.ui.vm.BaseViewModel
import java.lang.reflect.ParameterizedType

abstract class BaseVmFragment<VM : BaseViewModel, VB : ViewDataBinding> : BaseFragment<VB>(){

    lateinit var mViewModel : VM
    override fun setVm() {
        mViewModel = ViewModelProvider(this)[getVmClazz(this)]
    }

    @Suppress("UNCHECKED_CAST")
    fun <VM> getVmClazz(obj: Any): VM {
        return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as VM
    }

    fun showErrorMessage(message: String){
        Toast.makeText(requireContext(),message, Toast.LENGTH_LONG).show()
    }
}
