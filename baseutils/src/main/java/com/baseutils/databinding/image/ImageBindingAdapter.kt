package com.baseutils.databinding.image

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Created by Shivam Verma on 03/10/17.
 * Author: Shivam Verma
 * Project: ExpensesApp
 */
object ImageBindingAdapter {

    @JvmStatic
    @BindingAdapter("app:imageUrl", "app:error")
    fun setImageUrl(imageView: ImageView?, url: String?, drawable: Drawable) {
        if (imageView != null) {
            val context = imageView.context
            if (url == null)
                imageView.setImageDrawable(drawable)
            else
                Glide.with(context).load(url).placeholder(drawable).dontAnimate().into(imageView)
        }
    }

    @JvmStatic
    @BindingAdapter("app:imageCircleUrl", "app:error")
    fun setCircleImageUrl(imageView: ImageView?, url: String?, drawable: Drawable) {
        if (imageView != null) {
            val context = imageView.context
            if (url == null)
                imageView.setImageDrawable(drawable)
            else
                Glide.with(context).load(url).placeholder(drawable).bitmapTransform(CropCircleTransformation(context)).dontAnimate().into(imageView)
        }
    }







    @JvmStatic
    @BindingAdapter("app:fabSrc")
    fun setImageRes(fab: FloatingActionButton?, @DrawableRes drawable: Int) {
        if (fab != null && drawable != 0)
            fab.setImageResource(drawable)
    }

    @JvmStatic
    @BindingAdapter("app:smoothScrollPosition")
    fun scrollToPosition(recyclerView: RecyclerView?, position: Int?) {
        recyclerView?.smoothScrollToPosition(position!!)
    }

}