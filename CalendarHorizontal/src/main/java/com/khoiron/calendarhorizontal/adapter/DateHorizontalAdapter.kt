package com.khoiron.calendarhorizontal.adapter

import android.view.View
import android.view.ViewGroup
import android.content.Context
import java.text.SimpleDateFormat
import android.view.LayoutInflater
import kotlin.collections.ArrayList
import com.khoiron.calendarhorizontal.R
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.khoiron.calendarhorizontal.model.DayDataModel
import kotlinx.android.synthetic.main.item_date_view.view.*
import com.khoiron.calendarhorizontal.callback.OnclickListenerRecyclerView
import com.khoiron.calendarhorizontal.utils.DatePickerKey

class DateHorizontalAdapter (var context: Context, var items: ArrayList<DayDataModel>, var fontDay:Int): RecyclerView.Adapter<DateHorizontalAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_date_view, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = items.get(position)
        holder.itemView.tv_item_date.setText(data.day.toString())

        if (fontDay!=-1){
            try {
                val typefaceTitle = ResourcesCompat.getFont(context, fontDay)
                holder.itemView.tv_item_date.setTypeface(typefaceTitle)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }

        if (data.date.before(DatePickerKey.minDate)&&(SimpleDateFormat("dd-MM-yyyy").format(DatePickerKey.minDate)!=data.fullDay)){
            holder.itemView.tv_item_date.setTextColor(context.resources.getColor(R.color.colorTextNonActived))
        }
        else if (data.date.after(DatePickerKey.maxDate)){
            holder.itemView.tv_item_date.setTextColor(context.resources.getColor(R.color.colorTextNonActived))
        }
        else{
            if (data.typeDay==DatePickerKey.DAY_NEXT_MONTH||data.typeDay==DatePickerKey.DAY_PREVIOUS_MONTH){
                holder.itemView.tv_item_date.setTextColor(context.resources.getColor(R.color.colorTextNonActived))
            }
            /*else if(data.typeDay==DatePickerKey.DAY_SUNDAY){
                holder.itemView.tv_item_date.setTextColor(context.resources.getColor(R.color.colorTextHoliday))
            }*/
            else {
                if (DatePickerKey.startSelectDate.isNotEmpty()){
                    if (data.fullDay==DatePickerKey.startSelectDate){
                        setViewSelected(holder.itemView)
                    }
                    else {
                        if (DatePickerKey.TYPE_SELECTED==DatePickerKey.SINGGLE_SELECTED){
                            holder.itemView.tv_item_date.setTextColor(context.resources.getColor(R.color.colorTextHeaderTitle))
                        }
                        else{
                            if (DatePickerKey.endSelectDate.isNotEmpty()){
                                if (data.date.after(SimpleDateFormat("dd-MM-yyyy").parse(DatePickerKey.startSelectDate))&&data.date.before(SimpleDateFormat("dd-MM-yyyy").parse(DatePickerKey.endSelectDate))){
                                    setViewSelected(holder.itemView)
                                }
                                else if(data.fullDay==DatePickerKey.endSelectDate){
                                    setViewSelected(holder.itemView)
                                }
                                else {
                                    holder.itemView.tv_item_date.setTextColor(context.resources.getColor(R.color.colorTextHeaderTitle))
                                }
                            }
                            else {
                                holder.itemView.tv_item_date.setTextColor(context.resources.getColor(R.color.colorTextHeaderTitle))
                            }
                        }
                    }
                }
                else {
                    holder.itemView.tv_item_date.setTextColor(context.resources.getColor(R.color.colorTextHeaderTitle))
                }
            }

            holder.itemView.setOnClickListener {
                onclick.callbackRecyclerView(-1,position)
            }
        }
    }

    private fun setViewSelected(itemView: View) {
        itemView.tv_item_date.background = ContextCompat.getDrawable(context, R.drawable.rounded_selected)
        itemView.tv_item_date.setTextColor(context.resources.getColor(R.color.colorWhite))
    }

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {

    }
}