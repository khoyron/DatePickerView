package com.khoiron.datepickerandroid

import android.content.Intent
import kotlinx.android.synthetic.main.activity_main.*
import com.khoiron.calendarhorizontal.utils.DatePickerKey
import com.khoiron.calendarhorizontal.callback.CallbackCalendarHorizontal

class SampleSliderDatePickerHorizontal : BaseActivity(),CallbackCalendarHorizontal {

    override fun getLayout(): Int { return R.layout.activity_main }

    var typeSelect = false
    var startDate  = ""
    var endDate    = ""

    override fun OnMain() {
        calendar_view_horizontal.callbackCalendarListener(this)
        typeSelect = intent.getBundleExtra("data").getBoolean("type_selected")
        if (typeSelect){
            calendar_view_horizontal.typeSelected(DatePickerKey.DOUBLE_SELECTED)
        }
        else {
            calendar_view_horizontal.typeSelected(DatePickerKey.SINGGLE_SELECTED)
        }
        // other feature
//        calendar_view_horizontal.setStartDateSelected(SimpleDateFormat("dd MM yyyy").parse("26 10 2020"))
//        calendar_view_horizontal.setEndDateSelected(SimpleDateFormat("dd MM yyyy").parse("27 10 2020"))
//        calendar_view_horizontal.setMinDate(SimpleDateFormat("dd MM yyyy").parse("25 10 2020"))
//        calendar_view_horizontal.setMaxDate(SimpleDateFormat("dd MM yyyy").parse("08 11 2020"))
//        calendar_view_horizontal.setFontMonthHeader(R.font.lato_italic)
//        calendar_view_horizontal.setFontTitleDayHeader(R.font.lato_italic)
//        calendar_view_horizontal.setFontYearHeader(R.font.lato_italic)
//        calendar_view_horizontal.setTextFontDay(R.font.lato_italic)
    }

    override fun startDate(string: String) {
        startDate = string
        if (!typeSelect){
            delay(2000,object :DelayCallback{
                override fun done() {
                    val data = Intent()
                    data.putExtra("startDate",startDate)
                    finishResultOk(this@SampleSliderDatePickerHorizontal,data)
                }
            })
        }
    }

    override fun endDate(string: String) {
        endDate = string
        delay(2000,object :DelayCallback{
            override fun done() {
                val data = Intent()
                data.putExtra("startDate",startDate)
                data.putExtra("endDate",endDate)
                finishResultOk(this@SampleSliderDatePickerHorizontal,data)
            }
        })
    }
}
