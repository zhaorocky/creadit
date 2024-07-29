package com.ecuador.mvvm.base.net

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.ecuador.mvvm.base.ui.BaseVmActivity
import com.ecuador.mvvm.base.base.BaseResult
import com.ecuador.mvvm.base.base.ResultState
import com.ecuador.mvvm.base.base.paresException
import com.ecuador.mvvm.base.base.paresResult
import com.ecuador.mvvm.base.ui.BaseVmFragment
import com.ecuador.mvvm.base.ui.BaseVmLoaderListActivity
import com.ecuador.mvvm.base.util.AppException
import com.ecuador.mvvm.base.ui.vm.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


fun <T> BaseViewModel.request(
    block: suspend () -> BaseResult<T?>,
    resultState: MutableLiveData<ResultState<T?>>,
    isShowDialog: Boolean = false,
    loadingMessage: String = "loading..."
): Job {

    return viewModelScope.launch {
        runCatching {
            if (isShowDialog) resultState.value = ResultState.onAppLoading(loadingMessage)
            block()
        }.onSuccess {
            resultState.paresResult(it)
        }.onFailure {
            it.printStackTrace()
            resultState.paresException(it)
        }
    }
}

fun <T> BaseViewModel.request(
    block: suspend () -> BaseResult<T?>,
    resultState: MutableLiveData<ResultState<T?>>,
): Job {

    return viewModelScope.launch {
        runCatching {
            resultState.value = ResultState.onAppLoading("")
            block()
        }.onSuccess {
            resultState.paresResult(it)
        }.onFailure {
            it.printStackTrace()
            resultState.paresException(it)
        }
    }
}

fun <T> LiveData<T>.observeData(owner: LifecycleOwner, onChanged: (T) -> Unit) {
    observe(owner, Observer {
        data -> data?.let {
            onChanged(it) }
    })
}

fun <T> BaseVmActivity<*, *>.parseState(
    resultState: ResultState<T?>,
    onSuccess: (T?) -> Unit,
    onError: ((AppException) -> Unit)? = null,
) {
    when (resultState) {
        is ResultState.Loading -> {
            showLoading()
        }
        is ResultState.Success -> {

            run {
                dismissDialog()
                onSuccess(resultState.data)
            }
        }
        is ResultState.TokenError -> {
            dismissDialog()

            //finish()
        }
        is ResultState.Error -> {
            dismissDialog()
            onError?.run { this(resultState.errorMessage) }
        }
    }
}

fun <T> BaseVmLoaderListActivity<*, *>.parseState(
    resultState: ResultState<T?>,
    onSuccess: (T?) -> Unit,
    onError: ((AppException) -> Unit)? = null,
) {
    when (resultState) {
        is ResultState.Loading -> {
            showLoading()
        }
        is ResultState.Success -> {

            run {
                dismissDialog()
                onSuccess(resultState.data)
            }
        }
        is ResultState.TokenError -> {
            dismissDialog()

            //finish()
        }
        is ResultState.Error -> {
            dismissDialog()
            onError?.run { this(resultState.errorMessage) }
        }
    }
}

fun <T> BaseVmFragment<*, *>.parseState(
    resultState: ResultState<T?>,
    onSuccess: (T?) -> Unit,
    onError: ((AppException) -> Unit)? = null,
) {
    when (resultState) {
        is ResultState.Loading -> {
            showLoading()
        }
        is ResultState.TokenError -> {
            dismissDialog()

        }
        is ResultState.Success -> {
            dismissDialog()
            onSuccess(resultState.data)
        }
        is ResultState.Error -> {
            dismissDialog()
            onError?.run { this(resultState.errorMessage) }
        }
    }
}


