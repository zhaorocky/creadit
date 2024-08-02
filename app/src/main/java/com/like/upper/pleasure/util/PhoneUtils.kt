package com.like.upper.pleasure.util

import android.content.Context
import android.provider.ContactsContract
import androidx.core.database.getIntOrNull
import androidx.core.database.getStringOrNull
import org.json.JSONArray
import org.json.JSONObject

/**
 * 获取联系人  切记要加权限 android.permission.READ_CONTACTS
 * 可用easypermisson 框架进行处理
 */
class PhoneUtils {
    private var name: JSONObject? = null
    private var phoneArray: JSONArray? = null

    /**
     * 查询联系人数据
     * @param context Context
     */
    fun queryContactData(context: Context): JSONArray {
        val jsonArray = JSONArray()
        //记录raw_contact_id
        var rawContactIdOld = ""
        val cursorData = context.contentResolver.query(
            ContactsContract.Data.CONTENT_URI,
            arrayOf(
                ContactsContract.Data.RAW_CONTACT_ID,
                ContactsContract.Data.MIMETYPE,
                ContactsContract.Data.DATA1,
                ContactsContract.Data.DATA2,
                ContactsContract.Data.DATA3,
                ContactsContract.Data.DATA4,
                ContactsContract.Data.DATA5,
                ContactsContract.Data.DATA6,
                ContactsContract.CommonDataKinds.Photo.PHOTO
            ),
            null,
            null,
            "${ContactsContract.Data.RAW_CONTACT_ID} ASC"//按raw_contact_id升序排序
        )
        cursorData?.run {
            while (moveToNext()) {
                //获取当前的联系人raw_contact_id
                val rawContactId =
                    cursorData.getStringOrNull(getColumnIndex(ContactsContract.Data.RAW_CONTACT_ID))
                        ?: ""

                if (rawContactId != rawContactIdOld) {//判断当前的和上一次的raw_contact_id是否一样,不一样说明不是同一个联系人数据
                    if (rawContactIdOld.isNotEmpty()) {
                        //添加联系人到数据中
                        addContactJSONArray(jsonArray)
                    }

                    name = JSONObject()
                    phoneArray = JSONArray()

                    rawContactIdOld = rawContactId
                }
                when (cursorData.getStringOrNull(getColumnIndex(ContactsContract.Data.MIMETYPE))) {
                    //添加名字
                    ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE -> {
                        try {
                            name?.apply {
                                put(
                                    "display_name",
                                    cursorData.getStringOrNull(getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME))
                                )
                                put(
                                    "given_name",
                                    cursorData.getStringOrNull(getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME))
                                )
                                put(
                                    "family_name",
                                    cursorData.getStringOrNull(getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME))
                                )
                                put(
                                    "prefix",
                                    cursorData.getStringOrNull(getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.PREFIX))
                                )
                                put(
                                    "middle_name",
                                    cursorData.getStringOrNull(getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.MIDDLE_NAME))
                                )
                                put(
                                    "suffix",
                                    cursorData.getStringOrNull(getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.SUFFIX))
                                )
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    //添加电话号码
                    ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE -> {
                        try {
                            JSONObject().apply {
                                put(
                                    "number",
                                    cursorData.getStringOrNull(getColumnIndex(ContactsContract.Data.DATA1))
                                )
                                put(
                                    "type",
                                    cursorData.getIntOrNull(getColumnIndex(ContactsContract.Data.DATA2))
                                )
                                phoneArray?.put(this)
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
                if (cursorData.isLast) {
                    //添加联系人到数据中
                    addContactJSONArray(jsonArray)
                }
            }
            close()
        }
        return jsonArray
    }

    /**
     * 添加联系人到数据中
     * @param jsonArray JSONArray
     */
    private fun addContactJSONArray(jsonArray: JSONArray) {
        JSONObject().apply {
            put("name", name)
            put("phone", phoneArray)
            jsonArray.put(this)
        }
    }

}