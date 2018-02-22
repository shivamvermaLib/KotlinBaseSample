package com.baseutils.recyclerview

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Created by Shivam Verma on 15/09/17.
 * Author: Shivam Verma
 * Project: UtilsLibrary
 */

abstract class BaseRecyclerViewAdapter<BD : ViewDataBinding, T>(@LayoutRes private var layoutId: Int, var mList: ArrayList<T> = ArrayList()) : RecyclerView.Adapter<BaseRecyclerViewAdapter<BD, T>.BaseRecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseRecyclerViewHolder =
            BaseRecyclerViewHolder(DataBindingUtil.inflate<BD>(LayoutInflater.from(parent?.context), layoutId, parent, false))

    var baseRecyclerViewItemListener: BaseRecyclerViewItemListener? = null

    fun addItem(t: T, position: Int? = null) {
        when (position) {
            null -> mList.add(t)
            else -> mList.add(position, t)
        }
        notifyItemInserted(position ?: mList.size-1)
        baseRecyclerViewItemListener?.addItem(position ?: mList.size-1)
    }

    open fun getItem(position: Int): T = mList[position]

    fun findItem(predicate: (T) -> Boolean): T? = mList.find(predicate)

    fun itemPosition(t: T): Int = mList.indexOf(t)

    fun removeItem(position: Int) {
        if (position < 0) return
        mList.removeAt(position)
        notifyItemRemoved(position)
        baseRecyclerViewItemListener?.removeItem(position)
    }

    fun changeItem(position: Int, t: T) {
        mList[position] = t
        notifyItemChanged(position)
        baseRecyclerViewItemListener?.changeItem(position)
    }

    fun addItems(t: List<T>) {
        mList.addAll(t)
        notifyDataSetChanged()
        baseRecyclerViewItemListener?.addAllItems()
    }

    override fun getItemCount(): Int = mList.size

    fun getAllItems(): ArrayList<T> = mList

    open fun clear() {
        mList.clear()
        notifyDataSetChanged()
    }

    inner class BaseRecyclerViewHolder(var binding: BD) : RecyclerView.ViewHolder(binding.root)
}
