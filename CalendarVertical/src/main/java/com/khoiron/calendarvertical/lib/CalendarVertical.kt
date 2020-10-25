package com.khoiron.calendarvertical.lib

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.khoiron.calendarvertical.R
import com.khoiron.calendarvertical.adapter.CalendarVerticalAdapter
import com.khoiron.calendarvertical.callback.OnclickRecyclerViewParent
import com.khoiron.calendarvertical.model.CalendarVerticalModel
import com.khoiron.calendarvertical.utils.*
import com.khoiron.calendarvertical.utils.CodeDatePicker.endSelectDate
import com.khoiron.calendarvertical.utils.CodeDatePicker.startSelectDate
import kotlinx.android.synthetic.main.calendar_vertical_view.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CalendarVertical : LinearLayout,
    View.OnClickListener,
    OnclickRecyclerViewParent {

    val adapter by lazy { CalendarVerticalAdapter(context) }
    lateinit var callback : CallbackCalendarVertical
    val data = ArrayList<CalendarVerticalModel>()
    var maxNextMonth = 0

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

        val styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.CalendarVertical)
        maxNextMonth = styledAttrs.getInt(R.styleable.CalendarVertical_maxNextMonth, 0)

        styledAttrs.recycle()

        init()
    }

    private fun init() {
        setOrientation(VERTICAL)
        View.inflate(context, R.layout.calendar_vertical_view, this)

        setDafaultData()
        initRecyclerView()
    }

    private fun setDafaultData() {
        startSelectDate = ""
        endSelectDate   = ""
        CodeDatePicker.minDate = Date()
        CodeDatePicker.maxDate = SimpleDateFormat("dd MM yyyy").parse("20 10 2050")
        CodeDatePicker.TYPE_SELECTED = CodeDatePicker.DOUBLE_SELECTED
        CodeDatePicker.formatDateOutput = ""
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_calendar.layoutManager = layoutManager
        rv_calendar.setItemAnimator(DefaultItemAnimator())
        rv_calendar.setHasFixedSize(true)
        rv_calendar.addItemDecoration(StickHeaderItemDecoration(adapter));
        rv_calendar.setAdapter(adapter)

        val mCalendar = Calendar.getInstance()

        rv_calendar.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    if (maxNextMonth==0){
                        GetDataCalendar().getDataDates(mCalendar, 6).forEach {
                            data.add(addDataHeader(it.date, it.dateStr))
                            data.add(it)
                        }
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })

        if (maxNextMonth!=0){
            GetDataCalendar().getDataDates(mCalendar, maxNextMonth).forEach {
                data.add(addDataHeader(it.date, it.dateStr))
                data.add(it)
            }
        }
        else {
            GetDataCalendar().getDataDates(mCalendar, 6).forEach {
                data.add(addDataHeader(it.date, it.dateStr))
                data.add(it)
            }
        }
/*
        GetDataCalendar().getDataDates(mCalendar,5).forEach {
            data.add(addDataHeader(it.date,it.dateStr))
            data.add(it)
        }*/

        adapter.setData(data)
        adapter.callbackRecyclerView(this)

    }

    private fun addDataHeader(date: Date, dateStr: String): CalendarVerticalModel {
        val data = CalendarVerticalModel(
            date,
            dateStr,
            ArrayList(),
            CodeDatePicker.VIEW_TYPE_HEADER
        )
        return data
    }

    override fun onClick(v: View?) {

    }

    fun callbackCalendarListener(callbackCalendar: CallbackCalendarVertical){
        callback = callbackCalendar
    }

    override fun callbackRecyclerView(
        viewParent: Int,
        positionParent: Int,
        view: Int,
        position: Int
    ) {


        when(viewParent){
            CodeDatePicker.ONCLICK_DATE -> {
                val mCalendar = Calendar.getInstance()
                if (startSelectDate.isEmpty()) {
                    if (!data[positionParent].data[position].date.before(mCalendar.time) || (SimpleDateFormat(
                            CodeDatePicker.formatDate
                        ).format(mCalendar.time) == data[positionParent].data[position].fullDay)
                    ) {
                        startSelectDate =
                            SimpleDateFormat(CodeDatePicker.formatDate).format(data[positionParent].data[position].date)
                        adapter.notifyDataSetChanged()

                        if (CodeDatePicker.formatDateOutput.isNotEmpty()) {
                            callback.startDate(
                                convertFormatDate(
                                    startSelectDate,
                                    CodeDatePicker.formatDate,
                                    CodeDatePicker.formatDateOutput
                                )
                            )
                        } else {
                            callback.startDate(startSelectDate)
                        }
                    }
                } else {
                    val dateSelected =
                        SimpleDateFormat(CodeDatePicker.formatDate).format(data[positionParent].data[position].date)
                    if (startSelectDate == dateSelected) {
                        startSelectDate = ""
                        endSelectDate = ""
                        adapter.notifyDataSetChanged()
                    } else {
                        if (CodeDatePicker.TYPE_SELECTED == CodeDatePicker.SINGGLE_SELECTED) {
                            startSelectDate = SimpleDateFormat(CodeDatePicker.formatDate).format(
                                data[positionParent].data[position].date
                            )
                            adapter.notifyDataSetChanged()
                            if (CodeDatePicker.formatDateOutput.isNotEmpty()) {
                                callback.startDate(
                                    convertFormatDate(
                                        startSelectDate,
                                        CodeDatePicker.formatDate,
                                        CodeDatePicker.formatDateOutput
                                    )
                                )
                            } else {
                                callback.startDate(startSelectDate)
                            }
                        } else {
                            if (data[positionParent].data[position].date.before(
                                    SimpleDateFormat(
                                        CodeDatePicker.formatDate
                                    ).parse(startSelectDate)
                                )
                            ) {
                                Toast.makeText(
                                    context,
                                    CodeDatePicker.titleWarningPreviousDate,
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                endSelectDate = dateSelected
                                adapter.notifyDataSetChanged()

                                if (CodeDatePicker.formatDateOutput.isNotEmpty()) {
                                    callback.startDate(
                                        convertFormatDate(
                                            startSelectDate,
                                            CodeDatePicker.formatDate,
                                            CodeDatePicker.formatDateOutput
                                        )
                                    )
                                    callback.endDate(
                                        convertFormatDate(
                                            endSelectDate,
                                            CodeDatePicker.formatDate,
                                            CodeDatePicker.formatDateOutput
                                        )
                                    )
                                } else {
                                    callback.startDate(startSelectDate)
                                    callback.endDate(endSelectDate)
                                }
                            }
                        }

                    }
                }
            }
        }
    }

    fun setMinDate(minDate: Date){
        CodeDatePicker.minDate = minDate
    }

    fun setMaxDate(maxDate: Date){
        CodeDatePicker.maxDate = maxDate
    }

    fun setStartDateSelected(date: Date){
        startSelectDate = SimpleDateFormat(CodeDatePicker.formatDate).format(date)
        adapter.notifyDataSetChanged()
    }

    fun setEndDateSelected(date: Date){
        endSelectDate = SimpleDateFormat(CodeDatePicker.formatDate).format(date)
        adapter.notifyDataSetChanged()
    }

    fun setTextFontDayHeader(fontDay: Int) {
        CodeDatePicker.fontDayHeader = fontDay
        adapter.notifyDataSetChanged()
    }

    fun setTextFontMonthHeader(fontMonth: Int) {
        CodeDatePicker.fontMonthHeader = fontMonth
        adapter.notifyDataSetChanged()
    }

    fun setTextFontDay(fontDay: Int) {
        CodeDatePicker.fontDay = fontDay
        adapter.notifyDataSetChanged()
    }

    fun typeSelected(type: Int){
        CodeDatePicker.TYPE_SELECTED = type
    }

    fun setFormatDateOutput(format: String){
        CodeDatePicker.formatDateOutput = format
    }

    fun setMassageWarningSelectPreviosDate(message: String){
        CodeDatePicker.titleWarningPreviousDate = message
    }

}