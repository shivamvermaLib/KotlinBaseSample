package com.baseutils.databinding.recyclerview

import android.databinding.BindingAdapter
import android.support.annotation.IdRes
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.baseutils.recyclerview.BaseRecyclerView
import com.baseutils.recyclerview.BaseRecyclerViewAdapter

/**
 * Created by Shivam Verma on 05/10/17.
 * Author: Shivam Verma
 * Project: ExpensesApp
 */
object RecyclerViewBindingAdapter {

    @JvmStatic
    @BindingAdapter("app:itemSpacing")
    fun setItemSpacing(baseRecyclerView: BaseRecyclerView, spacing: Float) {
        var span = 1
        val layoutManager = baseRecyclerView.layoutManager
        if (layoutManager is GridLayoutManager) {
            span = layoutManager.spanCount
        }
        baseRecyclerView.addItemDecoration(ItemDecorationGridColumns(spacing.toInt(), span))
    }


    @JvmStatic
    @BindingAdapter("app:adapter")
    fun setAdapter(recyclerView: RecyclerView?, baseRecyclerViewAdapter: BaseRecyclerViewAdapter<*, *>?) {
        if (baseRecyclerViewAdapter != null && recyclerView != null) {
            recyclerView.adapter = baseRecyclerViewAdapter
        }
    }

    @JvmStatic
    @BindingAdapter("app:empty_view")
    fun setEmptyView(recyclerView: BaseRecyclerView?, @IdRes emptyView: Int) {
        if (emptyView != 0 && recyclerView != null) {
            val view = (recyclerView.parent as View).findViewById<View>(emptyView)
            recyclerView.setEmptyView(view)
        }
    }
}