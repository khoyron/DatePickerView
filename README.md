# DatePickerView

[![API](https://img.shields.io/badge/API-16%2B-red.svg?style=flat)](https://android-arsenal.com/api?level=16)
[![](https://jitpack.io/v/mkhoiron/Actionsheet-android.svg)](https://jitpack.io/#mkhoiron/Actionsheet-android/4)

Android date picker with custom view , can be for single selection or range selection , date result format etc.

<<<<<<< HEAD

<img align="left" width="220" src="https://raw.githubusercontent.com/khoyron/DatePickerView/master/image/calendar_vertical_sample.jpg"/>
<img align="left" width="250" src="https://raw.githubusercontent.com/khoyron/DatePickerView/master/image/calendar_horizontal_sample.jpg"/>
<img align="center" width="250" src="https://raw.githubusercontent.com/khoyron/DatePickerView/master/image/demo.gif?raw=true"></a>
<img align="left" width="220" src="https://raw.githubusercontent.com/khoyron/DatePickerView/master/image/calendar_vertical.jpg"/>
<img align="left" width="250" src="https://raw.githubusercontent.com/khoyron/DatePickerView/master/image/calendar_vertical.jpg"/>
<img align="left" width="250" src="https://raw.githubusercontent.com/khoyron/DatePickerView/master/image/calendar_horizontal_singel_selected.jpg"/>
<img align="center" width="250" src="https://raw.githubusercontent.com/khoyron/DatePickerView/master/image/calendar_horizontal_double_selected.jpg"/><br />

## Installation

-  Add the following to your project level `build.gradle`:
 
```gradle
allprojects {
	repositories {
		maven { url 'https://jitpack.io' }
	}
}
```
  -  Add this to your app `build.gradle`:
 
```gradle
dependencies {
	implementation 'com.github.khoyron:DatePickerView:CalendarVertical:1'
	implementation 'com.github.khoyron:DatePickerView:CalendarHorizontal:1'
}
```

## In xml

- calendar horizontal

```xml

<com.khoiron.calendarhorizontal.lib.CalendarHorizontal
        android:layout_width="match_parent"
        android:id="@+id/calendar_view_horizontal"
        android:layout_height="wrap_content">
</com.khoiron.calendarhorizontal.lib.CalendarHorizontal>

```

- calendar vertical

```xml

<com.khoiron.calendarvertical.lib.CalendarVertical
        android:id="@+id/calendar_view_vertical"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
</com.khoiron.calendarvertical.lib.CalendarVertical>
   
```

## Using Kotlin

```kotlin

       calendar_view_horizontal.typeSelected(DatePickerKey.SINGGLE_SELECTED) // or CodeDatePicker.DOUBLE_SELECTED
       calendar_view_horizontal.callbackCalendarListener(object :CallbackCalendarHorizontal{
            override fun startDate(string: String) {
                
            }

            override fun endDate(string: String) {
                
            }

        })
        
      calendar_view_vertical.typeSelected(DatePickerKey.SINGGLE_SELECTED) // or CodeDatePicker.DOUBLE_SELECTED
      calendar_view_vertical.callbackCalendarListener(object :CallbackCalendarHorizontal{
            override fun startDate(string: String) {
                
            }

            override fun endDate(string: String) {
                
            }

      })
      
      //  other feature
//        calendar_view.setStartDateSelected(SimpleDateFormat("dd MM yyyy").parse("24 09 2020"))
//        calendar_view.setEndDateSelected(SimpleDateFormat("dd MM yyyy").parse("27 09 2020"))
//        calendar_view.setMinDate(SimpleDateFormat("dd MM yyyy").parse("24 09 2020"))
//        calendar_view.setMaxDate(SimpleDateFormat("dd MM yyyy").parse("30 09 2020"))
//        calendar_view.setTextFontDayHeader(R.font.lato_italic)
//        calendar_view.setTextFontMonthHeader(R.font.lato_italic)
//        calendar_view.setTextFontDay(R.font.lato_italic)
        
```

##### Available feature

Function      		               | description
---------------------------------------| -------------
`setStartDateSelected()`  	       | set start selected date
`setEndDateSelected()`                 | set end selected date
`setMinDate()` 	                       | set min date view select
`setMaxDate()`                         | set max date view select
`setFormatDateOutput()`  	       | set format date for output
`setMessageWarningSelectPreviosDate()` | set message warning select previous date
`typeSelected()`  		       | set type selected, singel or double select
`setTextFontMonthHeader(min, max)`     | set font month header
`setTextFontDay()`	               | set font day
`setTextFontDayHeader()`	       | set font title day calendar vertical
`setFontTitleDayHeader()`              | set font title day calendar horizontal



License
=======
Copyright 2020 [M Khoiron](https://linkedin.com/in/khoiron)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Al-hamdulillahi Rabbil 'alamin And Special thanks to [JetBrains](https://github.com/JetBrains) and [jitpack.io](https://github.com/jitpack-io) for their contributions to this project.
