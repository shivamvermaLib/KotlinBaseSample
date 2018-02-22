package com.baseutils.base

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.databinding.ViewDataBinding
import android.support.annotation.StringRes
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


/**
 * Created by Shivam Verma on 04/10/17.
 * Author: Shivam Verma
 * Project: ExpensesApp
 */
open class BaseViewModel() : ViewModel() {

    internal var toast = MutableLiveData<Int>()
    internal var keyboard = MutableLiveData<Boolean>()
    private val compositeDisposable = CompositeDisposable()

    protected fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    protected fun showToast(@StringRes message: Int) {
        toast.postValue(message)
    }

    protected fun showKeyboard() {
        keyboard.postValue(true)
    }

    protected fun hideKeyboard() {
        keyboard.postValue(false)
    }

    /**
     * A creator is used to inject the product ID into the ViewModel
     *
     *
     * This creator is to showcase how to inject dependencies into ViewModels. It's not
     * actually necessary in this case, as the product ID can be passed in a public method.
     */
    abstract class Factory<DB : ViewDataBinding, VM : ViewModel> : ViewModelProvider.NewInstanceFactory() {
        abstract fun getClassInstance(): BaseViewModel

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return getClassInstance() as T
        }
    }
}