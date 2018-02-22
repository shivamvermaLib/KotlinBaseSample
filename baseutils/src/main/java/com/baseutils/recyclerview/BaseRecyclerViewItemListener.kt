package com.baseutils.recyclerview

/**
 * Created by Shivam Verma on 15/09/17.
 * Author: Shivam Verma
 * Project: UtilsLibrary
 */
interface BaseRecyclerViewItemListener {
    fun addItem(position: Int)
    fun removeItem(removeItem: Int)
    fun changeItem(removeItem: Int) {

    }

    fun addAllItems() {

    }
}