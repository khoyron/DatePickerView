package com.khoiron.calendarvertical.utils

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

fun assignPad10(number: Int) = if (number < 10) number.toString().padStart(2, '0') else number.toString()
fun convertFormatDate(stringDate :String, formatInput:String, formatOutput:String):String{
    try {
        val cal = Calendar.getInstance();
        val sdf = SimpleDateFormat(formatInput)//"yyyy-MM-dd"
        cal.setTime(sdf.parse(stringDate))
        val format = SimpleDateFormat(formatOutput, Locale.ENGLISH)//"EEEE, yyyy-MM-dd"
        return format.format(cal.getTime())
    }catch (e: Exception){
        return CodeDatePicker.formatDate
    }
}