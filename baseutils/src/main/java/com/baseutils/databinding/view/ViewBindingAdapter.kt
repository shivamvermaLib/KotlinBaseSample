package com.shivam.baseutils.databinding

import android.R
import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.view.View
import android.support.annotation.DrawableRes
import android.support.design.widget.FloatingActionButton
import android.widget.ArrayAdapter
import android.widget.Spinner


/**
 * Created by Shivam Verma on 03/10/17.
 * Author: Shivam Verma
 * Project: ExpensesApp
 */
object ViewBindingAdapter {


    @JvmStatic
    @BindingAdapter("app:visibleInvisible")
    fun setVisible(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.INVISIBLE
    }

    @JvmStatic
    @BindingAdapter("app:visibleGone")
    fun setVisibleGone(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }


    @JvmStatic
    @BindingAdapter("app:fabShow")
    fun setFabVisible(fab: FloatingActionButton, visible: Boolean) {
        when {
            visible -> fab.show()
            else -> fab.hide()
        }
    }
    @JvmStatic
    @BindingAdapter("app:fabSrc")
    fun setImageRes(fab: FloatingActionButton, drawable: Drawable) {
        fab.setImageDrawable(drawable)
    }

    @JvmStatic
    @BindingAdapter("app:entries")
    fun setSpinnerStringAdapter(spinner: Spinner, list: List<*>?) {
        if (list != null) {
            val adapter = ArrayAdapter(spinner.context, R.layout.simple_spinner_item, list)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }
}