package com.like.upper.pleasure.util

import android.icu.math.BigDecimal
import java.text.DecimalFormat

class NubmerUtils {

    companion object{

        fun numStringToStringNum1000(num : String?) : String{
            try {
                val decimalFormat = DecimalFormat("#,###.##")
                num?.let {
                    return decimalFormat.format(BigDecimal(it))
                }
            }catch (e : Exception){
                e.toString()
            }
           return ""
        }


    }
}