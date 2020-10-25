package com.khoiron.calendarvertical.adapter

import android.view.View
import android.view.ViewGroup
import android.content.Context
import java.text.SimpleDateFormat
import android.view.LayoutInflater
import kotlin.collections.ArrayList
import com.khoiron.calendarvertical.R
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.khoiron.calendarvertical.callback.OnclickRecyclerView
import com.khoiron.calendarvertical.utils.CodeDatePicker
import com.khoiron.calendarvertical.model.DayDataVerticalModel
import kotlinx.android.synthetic.main.item_date_vertical.view.*

class DateVerticalAdapter (var context: Context, var items: ArrayList<DayDataVerticalModel>): RecyclerView.Adapter<DateVerticalAdapter.ViewHolder>() {

    lateinit var onclick: OnclickRecyclerView

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_date_vertical, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = items.get(position)
        holder.itemView.tv_item_date.setText(data.day.toString())

        if (CodeDatePicker.fontDay!=-1){
            try {
                val typefaceTitle = ResourcesCompat.getFont(context, CodeDatePicker.fontDay)
                holder.itemView.tv_item_date.setTypeface(typefaceTitle)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }

        if (data.date.before(CodeDatePicker.minDate)&&(SimpleDateFormat("dd-MM-yyyy").format(CodeDatePicker.minDate)!=data.fullDay)){
            holder.itemView.tv_item_date.setTextColor(context.resources.getColor(R.color.colorDayNonActived))
        }
        else if (data.date.after(CodeDatePicker.maxDate)){
            holder.itemView.tv_item_date.setTextColor(context.resources.getColor(R.color.colorDayNonActived))
        }
        else{
            if (data.typeDay==CodeDatePicker.DAY_NEXT_MONTH||data.typeDay==CodeDatePicker.DAY_PREVIOUS_MONTH){
                holder.itemView.tv_item_date.setTextColor(context.resources.getColor(R.color.colorDayNonActived))
            }
            else if(data.typeDay==CodeDatePicker.DAY_SUNDAY){
                holder.itemView.tv_item_date.setTextColor(context.resources.getColor(R.color.colorTextHoliday))
            }
            else {
                if (CodeDatePicker.startSelectDate.isNotEmpty()){
                    if (data.fullDay==CodeDatePicker.startSelectDate){
                        holder.itemView.tv_item_date.setTextColor(context.resources.getColor(R.color.colorPrimary))
                    }
                    else {
                        if (CodeDatePicker.TYPE_SELECTED==CodeDatePicker.SINGGLE_SELECTED){
                            holder.itemView.tv_item_date.setTextColor(context.resources.getColor(R.color.colorDayActived))
                        }
                        else{
                            if (CodeDatePicker.endSelectDate.isNotEmpty()){
                                if (data.date.after(SimpleDateFormat("dd-MM-yyyy").parse(CodeDatePicker.startSelectDate))&&data.date.before(SimpleDateFormat("dd-MM-yyyy").parse(CodeDatePicker.endSelectDate))){
                                    holder.itemView.tv_item_date.setTextColor(context.resources.getColor(R.color.colorPrimary))
                                }
                                else if(data.fullDay==CodeDatePicker.endSelectDate){
                                    holder.itemView.tv_item_date.setTextColor(context.resources.getColor(R.color.colorPrimary))
                                }
                                else {
                                    holder.itemView.tv_item_date.setTextColor(context.resources.getColor(R.color.colorDayActived))
                                }
                            }
                            else {
                                holder.itemView.tv_item_date.setTextColor(context.resources.getColor(R.color.colorDayActived))
                            }
                        }
                    }
                }
                else {
                    holder.itemView.tv_item_date.setTextColor(context.resources.getColor(R.color.colorDayActived))
                }
            }

            holder.itemView.setOnClickListener {
                onclick.callbackRecyclerView(-1,position)
            }
        }
    }

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {

    }
}