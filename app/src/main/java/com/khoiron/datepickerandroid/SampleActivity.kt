package com.khoiron.datepickerandroid

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.content.Intent
import android.widget.LinearLayout
import com.khoiron.calendarhorizontal.utils.convertFormatDate
import kotlinx.android.synthetic.main.sample_activity_view.*

class SampleActivity : BaseActivity() {

    override fun getLayout(): Int {
        return R.layout.sample_activity_view
    }

    var double_selected_horizontal = true
    var double_selected_vertical   = true

    override fun OnMain() {
        btn_switch_horizontal.setOnCheckedChangeListener { compoundButton, isChecked ->
            double_selected_horizontal = isChecked
            if (isChecked){
                tv_switch_horizontal.text = "Double Select"
                showLineDate(lay_parent_end_date)
            }
            else {
                tv_switch_horizontal.text = "Singel Select"
                hidenLineDate(lay_parent_end_date)
            }
        }

        btn_switch_vertical.setOnCheckedChangeListener { compoundButton, isChecked ->
            double_selected_vertical = isChecked
            if (isChecked){
                tv_switch_vertical.text = "Double Select"
                showLineDate(lay_parent_end_date_vertical)
            }
            else {
                tv_switch_vertical.text = "Singel Select"
                hidenLineDate(lay_parent_end_date_vertical)
            }
        }
    }

    private fun hidenLineDate(linearLayout: LinearLayout) {
        linearLayout.visibility  = View.GONE
    }

    private fun showLineDate(linearLayout: LinearLayout) {
        linearLayout.visibility = View.VISIBLE
    }

    fun openCalendarVertical(view: View){
        val bundle = Bundle()
        bundle.putBoolean("type_selected",double_selected_vertical)
        gotoActivityResultWithBundle(SampleSliderDatePickerVertical::class.java,bundle,1)
    }

    fun openCalendarHorizontal(view: View){
        val bundle = Bundle()
        bundle.putBoolean("type_selected",double_selected_horizontal)
        gotoActivityResultWithBundle(SampleSliderDatePickerHorizontal::class.java,bundle,2)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode==Activity.RESULT_OK){
            when(requestCode){
                1 -> {
                    tv_start_dates.text = convertFormatDate(data?.getStringExtra("startDate").toString(),"dd-MM-yyyy","EEEE, dd MMM yyyy")
                    tv_end_dates.text   = convertFormatDate(data?.getStringExtra("endDate").toString(),"dd-MM-yyyy","EEEE, dd MMM yyyy")
                }
                2 -> {
                    tv_start_date.text  = convertFormatDate(data?.getStringExtra("startDate").toString(),"dd-MM-yyyy","EEEE, dd MMM yyyy")
                    tv_end_date.text   = convertFormatDate(data?.getStringExtra("endDate").toString(),"dd-MM-yyyy","EEEE, dd MMM yyyy")
                }
            }
        }
    }
}