package com.khoiron.calendarvertical.model

import java.util.*
import kotlin.collections.ArrayList

data class CalendarVerticalModel(var date        : Date = Date(),
                                 var dateStr     : String = "",
                                 var data        : ArrayList<DayDataVerticalModel> = ArrayList(),
                                 var typeLayout  : Int
    )