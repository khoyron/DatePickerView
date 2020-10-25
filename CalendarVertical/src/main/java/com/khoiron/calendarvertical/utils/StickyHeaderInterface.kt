package com.khoiron.contact.module

import android.view.View

interface StickyHeaderInterface {
    fun getHeaderPositionForItem(itemPosition: Int): Int
    fun getHeaderLayout(headerPosition: Int): Int
    fun bindHeaderData(header: View, headerPosition: Int)
    fun isHeader(itemPosition: Int): Boolean
}