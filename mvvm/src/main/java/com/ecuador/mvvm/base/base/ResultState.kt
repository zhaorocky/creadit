package com.ecuador.mvvm.base.base

import androidx.lifecycle.MutableLiveData
import com.ecuador.mvvm.base.util.AppException
import com.google.gson.JsonParseException
import retrofit2.HttpException
import java.net.ConnectException

sealed class ResultState<out T> {

    companion object{
        fun <T> onAppSuccess(data: T?): ResultState<T> = Success(data)
        fun <T> onAppLoading(loadingMessage: String): ResultState<T> = Loading(loadingMessage)
        fun <T> onAppError(error: AppException): ResultState<T> = Error(error)
        fun <T> onTokenError(error: Boolean): ResultState<T> = TokenError(false)
    }

    data class Loading(val loadMessage : String) : ResultState<Nothing>()
    data class Success<out T>(val data : T?) : ResultState<T>()
    data class Error(val errorMessage : AppException) : ResultState<Nothing>()
    data class TokenError(val error : Boolean) : ResultState<Nothing>()
}

fun <T> MutableLiveData<ResultState<T?>>.paresResult(result: BaseResult<T?>?) {


    value = when(result?.illPlainLameEnquiry) {
        1000 -> {
            ResultState.onAppSuccess(result.foggyKnifeSkyscraper)
        }
        2001 -> {
            ResultState.onTokenError(false)
        }
        else -> {
            val code = if (result?.illPlainLameEnquiry != null) result.illPlainLameEnquiry else -1
            val message = if (result?.blackHobbyBackLiterature != null) result.blackHobbyBackLiterature else "Unknown Error"
            ResultState.onAppError(AppException(code!! , message!!))
        }
    }
}

fun <T> MutableLiveData<ResultState<T>>.paresException(e: Throwable) {
    var messge: String? = "Network Error"
    when(e){
        is JsonParseException ->{
            messge = "Parse Error"
        }
        is HttpException ->{
            messge = "Connect Error"
        }
        is ConnectException ->{
            messge = "Connect Error"
        }else ->{
            messge = "Network Error"
        }
    }

    this.value = ResultState.onAppError(AppException(-1 , messge))
}
