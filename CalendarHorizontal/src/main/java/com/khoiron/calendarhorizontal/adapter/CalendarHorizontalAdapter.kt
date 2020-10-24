package com.khoiron.calendarhorizontal.adapter

import android.view.View
import android.view.ViewGroup
import android.content.Context
import android.view.LayoutInflater
import kotlin.collections.ArrayList
import com.khoiron.calendarhorizontal.R
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.GridLayoutManager
import com.khoiron.calendarhorizontal.model.CalendarModel
import com.khoiron.calendarhorizontal.utils.DatePickerKey
import kotlinx.android.synthetic.main.item_calendar.view.*
import com.khoiron.calendarhorizontal.callback.OnclickListenerRecyclerView
import com.khoiron.calendarhorizontal.callback.OnclickListenerRecyclerViewParent

class CalendarHorizontalAdapter (var context: Context): RecyclerView.Adapter<CalendarHorizontalAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerViewParent
    var items = ArrayList<CalendarModel>()
    var fontDays = -1

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_calendar, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerViewParent){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, positionParent: Int) {

        val data = items.get(positionParent)
        val dateAdapter by lazy { DateHorizontalAdapter(context,data.data,fontDays) }
        val mLayoutManager = GridLayoutManager(context, 7)
        holder.itemView.rv_calendar_horizontal.setLayoutManager(mLayoutManager)
        holder.itemView.rv_calendar_horizontal.setHasFixedSize(true)
        holder.itemView.rv_calendar_horizontal.setAdapter(dateAdapter)
        holder.itemView.rv_calendar_horizontal.setNestedScrollingEnabled(false)

        dateAdapter.setOnclickListener(object : OnclickListenerRecyclerView {
            override fun callbackRecyclerView(view: Int, position: Int) {
                onclick.callbackRecyclerView(DatePickerKey.ONCLICK_DATE,positionParent,-1,position)
            }
        })
    }

    fun setData(data:ArrayList<CalendarModel>){
        items = data
        notifyDataSetChanged()
    }

    fun setFontDay(fontDay: Int) {
        fontDays = fontDay
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {

    }
}