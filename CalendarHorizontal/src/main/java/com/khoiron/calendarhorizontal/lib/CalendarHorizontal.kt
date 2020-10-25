package com.khoiron.calendarhorizontal.lib

import java.util.*
import android.view.View
import android.widget.Toast
import android.content.Context
import android.widget.TextView
import android.util.AttributeSet
import java.text.SimpleDateFormat
import android.widget.LinearLayout
import kotlin.collections.ArrayList
import com.khoiron.calendarhorizontal.R
import androidx.viewpager2.widget.ViewPager2
import android.view.animation.AnimationUtils
import androidx.core.content.res.ResourcesCompat
import com.khoiron.calendarhorizontal.utils.DataCalendar
import com.khoiron.calendarhorizontal.utils.DatePickerKey
import com.khoiron.calendarhorizontal.model.CalendarModel
import kotlinx.android.synthetic.main.calendar_view.view.*
import kotlinx.android.synthetic.main.header_calendar.view.*
import com.khoiron.calendarhorizontal.utils.convertFormatDate
import com.khoiron.calendarhorizontal.adapter.CalendarHorizontalAdapter
import com.khoiron.calendarhorizontal.callback.CallbackCalendarHorizontal
import com.khoiron.calendarhorizontal.callback.OnclickListenerRecyclerViewParent

class CalendarHorizontal : LinearLayout,
    View.OnClickListener, OnclickListenerRecyclerViewParent {

    lateinit var callback : CallbackCalendarHorizontal
    val adapter = CalendarHorizontalAdapter(context)
    lateinit var data: ArrayList<CalendarModel>
    var maxNextMonth = 0

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

        val styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.CalendarHorizontal)
        maxNextMonth = styledAttrs.getInt(R.styleable.CalendarHorizontal_maxNextMonth,0)
        styledAttrs.recycle()

        init()
    }

    private fun init() {
        setOrientation(VERTICAL)
        View.inflate(context, R.layout.calendar_view, this)

        setDafaultData()
        initPagerView()
    }

    private fun setDafaultData() {
        DatePickerKey.startSelectDate = ""
        DatePickerKey.endSelectDate = ""
        DatePickerKey.minDate = Date()
        DatePickerKey.maxDate = SimpleDateFormat("dd MM yyyy").parse("20 10 2050")
        DatePickerKey.TYPE_SELECTED = DatePickerKey.DOUBLE_SELECTED
        DatePickerKey.formatDateOutput = ""
    }

    private fun initPagerView() {
        //set data calendar
        val mCalendar = Calendar.getInstance()
        if (maxNextMonth!=0){
            data = DataCalendar().getDataDates(mCalendar,maxNextMonth)
        }
        else {
            data = DataCalendar().getDataDates(mCalendar,6)
        }
        adapter.setData(data)
        adapter.setOnclickListener(this)

        //init ViewPager
        vp_calendar.adapter  = adapter
        vp_calendar.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tv_title_month.text = SimpleDateFormat("MMM").format(data[position].date)
                tv_title_year.text  = SimpleDateFormat("yyyy").format(data[position].date)
                val anim = AnimationUtils.loadAnimation(context, R.anim.slide_left_in)
                tv_title_month.startAnimation(anim)

                if (position==(data.size-1)){
                    if (maxNextMonth==0){
                        data.addAll(DataCalendar().getDataDates(mCalendar,5))
                        adapter.notifyDataSetChanged()
                    }
                }
                super.onPageSelected(position)
            }
        })
    }

    override fun onClick(v: View?) {

    }

    fun callbackCalendarListener(callbackCalendar : CallbackCalendarHorizontal){
        callback = callbackCalendar
    }


    fun setMinDate(minDate: Date){
        DatePickerKey.minDate = minDate
    }

    fun setMaxDate(maxDate : Date){
        DatePickerKey.maxDate = maxDate
    }

    fun setStartDateSelected(date: Date){
        DatePickerKey.startSelectDate = SimpleDateFormat(DatePickerKey.formatDate).format(date)
        adapter.notifyDataSetChanged()
    }

    fun setEndDateSelected(date: Date){
        DatePickerKey.endSelectDate = SimpleDateFormat(DatePickerKey.formatDate).format(date)
        adapter.notifyDataSetChanged()
    }

    fun setFontYearHeader(fontYear: Int) {
        setTextFont(tv_title_year,fontYear)
    }

    fun setFontMonthHeader(fontMonth: Int){
        setTextFont(tv_title_month,fontMonth)
    }

    fun setFontTitleDayHeader(fontTitleDay: Int) {
        setTextFont(tv_title_month,fontTitleDay)
        setTextFont(tv_day_sun,fontTitleDay)
        setTextFont(tv_day_mon,fontTitleDay)
        setTextFont(tv_day_thu,fontTitleDay)
        setTextFont(tv_day_wed,fontTitleDay)
        setTextFont(tv_day_tue,fontTitleDay)
        setTextFont(tv_day_fri,fontTitleDay)
        setTextFont(tv_day_sat,fontTitleDay)
    }

    private fun setTextFont(textView: TextView,fontDay: Int) {
        try {
            val typefaceTitle = ResourcesCompat.getFont(context, fontDay)
            textView.setTypeface(typefaceTitle)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }


    fun setTextFontDay(fontDay: Int) {
        adapter.setFontDay(fontDay)
    }

    fun typeSelected(type:Int){
        DatePickerKey.TYPE_SELECTED = type
    }

    fun setFormatDateOutput(format:String){
        DatePickerKey.formatDateOutput = format
    }

    fun setMessageWarningSelectPreviosDate(message:String){
        DatePickerKey.titleWarningPreviousDate = message
    }

    fun maxNextMonth(maxNext:Int){
        this.maxNextMonth = maxNext
        data.clear()
        val mCalendar = Calendar.getInstance()
        if (maxNextMonth!=0){
            data = DataCalendar().getDataDates(mCalendar,maxNextMonth)
        }
        else {
            data = DataCalendar().getDataDates(mCalendar,6)
        }
        adapter.setData(data)
    }

    override fun callbackRecyclerView(
        viewParent: Int,
        positionParent: Int,
        view: Int,
        position: Int
    ) {
        when(viewParent){
            DatePickerKey.ONCLICK_DATE -> {
                val mCalendar = Calendar.getInstance()
                if (DatePickerKey.startSelectDate.isEmpty()){
                    if (!data[positionParent].data[position].date.before(mCalendar.time)||(SimpleDateFormat(DatePickerKey.formatDate).format(mCalendar.time)==data[positionParent].data[position].fullDay)){
                        DatePickerKey.startSelectDate = SimpleDateFormat(DatePickerKey.formatDate).format(data[positionParent].data[position].date)
                        adapter.notifyDataSetChanged()

                        if (DatePickerKey.formatDateOutput.isNotEmpty()){
                            callback.startDate(convertFormatDate(DatePickerKey.startSelectDate,DatePickerKey.formatDate,DatePickerKey.formatDateOutput))
                        }else{
                            callback.startDate(DatePickerKey.startSelectDate)
                        }
                    }
                }
                else {
                    val dateSelected = SimpleDateFormat(DatePickerKey.formatDate).format(data[positionParent].data[position].date)
                    if (DatePickerKey.startSelectDate ==dateSelected){
                        DatePickerKey.startSelectDate = ""
                        DatePickerKey.endSelectDate = ""
                        adapter.notifyDataSetChanged()
                    }
                    else {
                        if (DatePickerKey.TYPE_SELECTED==DatePickerKey.SINGGLE_SELECTED){
                            DatePickerKey.startSelectDate = SimpleDateFormat(DatePickerKey.formatDate).format(data[positionParent].data[position].date)
                            adapter.notifyDataSetChanged()
                            if (DatePickerKey.formatDateOutput.isNotEmpty()){
                                callback.startDate(convertFormatDate(DatePickerKey.startSelectDate,DatePickerKey.formatDate,DatePickerKey.formatDateOutput))
                            }else{
                                callback.startDate(DatePickerKey.startSelectDate)
                            }
                        }else{
                            if (data[positionParent].data[position].date.before(SimpleDateFormat(DatePickerKey.formatDate).parse(
                                    DatePickerKey.startSelectDate
                                ))){
                                Toast.makeText(context,DatePickerKey.titleWarningPreviousDate,
                                    Toast.LENGTH_LONG).show()
                            }
                            else {
                                DatePickerKey.endSelectDate = dateSelected
                                adapter.notifyDataSetChanged()

                                if (DatePickerKey.formatDateOutput.isNotEmpty()){
                                    callback.startDate(convertFormatDate(DatePickerKey.startSelectDate,DatePickerKey.formatDate,DatePickerKey.formatDateOutput))
                                    callback.endDate(convertFormatDate(DatePickerKey.endSelectDate,DatePickerKey.formatDate,DatePickerKey.formatDateOutput))
                                }else{
                                    callback.startDate(DatePickerKey.startSelectDate)
                                    callback.endDate(DatePickerKey.endSelectDate)
                                }
                            }
                        }

                    }
                }

                if (data[positionParent].data[position].typeDay==DatePickerKey.DAY_PREVIOUS_MONTH){
                    if (positionParent!=0){
                        vp_calendar.setCurrentItem((positionParent-1),true)
                    }
                }

                if (data[positionParent].data[position].typeDay==DatePickerKey.DAY_NEXT_MONTH){
                    if (positionParent<data.size){
                        vp_calendar.setCurrentItem((positionParent+1),true)
                    }
                }
            }
        }
    }

}