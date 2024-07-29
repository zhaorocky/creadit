package com.ecuador.mvvm.base.util

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesHelper(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "my_prefs"
        private const val MY_LUANGE = "my_luange"
        private const val LOGIN_STATUS = "login_status"
        private const val HAVE_ACCEPT = "have_accept"
        private const val TOKEN = "token"
        private const val USERID = "user_id"
        private const val NEWCUST_FLAG = "newCustFlag"
        private const val TESTCUST_FLAG = "testCustFlag"
        private const val PHONE_NO = "phoneNo"
        private const val USERNAME = "userName"

        @Volatile
        private var instance: SharedPreferencesHelper? = null

        fun getInstance(context: Context): SharedPreferencesHelper {
            return instance ?: synchronized(this) {
                instance ?: SharedPreferencesHelper(context).also { instance = it }
            }
        }
    }

    var userId: String?
        get() = prefs.getString(USERID, "")
        set(value) = prefs.edit().putString(USERID, value).apply()

    var token: String?
        get() = prefs.getString(TOKEN, "")
        set(value) = prefs.edit().putString(TOKEN, value).apply()

    var myLuange: String?
        get() = prefs.getString(MY_LUANGE, "")
        set(value) = prefs.edit().putString(MY_LUANGE, value).apply()

    var newCustFlag: String?
        get() = prefs.getString(NEWCUST_FLAG, "")
        set(value) = prefs.edit().putString(NEWCUST_FLAG, value).apply()

    var testCustFlag: String?
        get() = prefs.getString(TESTCUST_FLAG, "")
        set(value) = prefs.edit().putString(TESTCUST_FLAG, value).apply()

    var phoneNo: String?
        get() = prefs.getString(PHONE_NO, "")
        set(value) = prefs.edit().putString(PHONE_NO, value).apply()

    var userName: String?
        get() = prefs.getString(USERNAME, "")
        set(value) = prefs.edit().putString(USERNAME, value).apply()

    var loginStatus: Boolean
        get() = prefs.getBoolean(LOGIN_STATUS, false)
        set(value) = prefs.edit().putBoolean(LOGIN_STATUS, value).apply()

    var haveAccept: Boolean
        get() = prefs.getBoolean(HAVE_ACCEPT, false)
        set(value) = prefs.edit().putBoolean(HAVE_ACCEPT, value).apply()

}