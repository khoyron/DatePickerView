package com.khoiron.datepickerandroid

import android.content.Intent
import java.text.SimpleDateFormat
import com.khoiron.calendarvertical.utils.CodeDatePicker
import kotlinx.android.synthetic.main.calendar_vertical_activity.*
import com.khoiron.calendarvertical.utils.CallbackCalendarVertical

class SampleSliderDatePickerVertical : BaseActivity(),CallbackCalendarVertical {
    override fun getLayout(): Int { return R.layout.calendar_vertical_activity }

    var typeSelect = false
    var startDate  = ""
    var endDate    = ""

    override fun OnMain() {
        calendar_view.callbackCalendarListener(this)
        calendar_view.setStartDateSelected(SimpleDateFormat("dd MM yyyy").parse("24 09 2020"))
        calendar_view.setEndDateSelected(SimpleDateFormat("dd MM yyyy").parse("27 09 2020"))
        typeSelect = intent.getBundleExtra("data").getBoolean("type_selected")
        if (typeSelect){
            calendar_view.typeSelected(CodeDatePicker.DOUBLE_SELECTED)
        }
        else{
            calendar_view.typeSelected(CodeDatePicker.SINGGLE_SELECTED)
        }
        // other feature
//        calendar_view.setMinDate(SimpleDateFormat("dd MM yyyy").parse("24 09 2020"))
//        calendar_view.setMaxDate(SimpleDateFormat("dd MM yyyy").parse("30 09 2020"))
//        calendar_view.setTextFontDayHeader(R.font.lato_italic)
//        calendar_view.setTextFontMonthHeader(R.font.lato_italic)
//        calendar_view.setTextFontDay(R.font.lato_italic)
    }

    override fun startDate(string: String) {
        startDate = string
        if (!typeSelect){
            delay(2000,object :DelayCallback{
                override fun done() {
                    val data = Intent()
                    data.putExtra("startDate",startDate)
                    finishResultOk(this@SampleSliderDatePickerVertical,data)
                }
            })
        }
    }

    override fun endDate(string: String) {
        endDate = string
        delay(3000,object :DelayCallback{
            override fun done() {
                val data = Intent()
                data.putExtra("startDate",startDate)
                data.putExtra("endDate",endDate)
                finishResultOk(this@SampleSliderDatePickerVertical,data)
            }
        })
    }

}