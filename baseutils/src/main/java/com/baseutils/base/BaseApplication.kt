package com.baseutils.base

import android.app.Application
import android.content.ContextWrapper
import android.os.StrictMode
import com.baseutils.sharedpreference.Prefs

/**
 * Created by Shivam Verma on 03/10/17.
 * Author: Shivam Verma
 * Project: ExpensesApp
 */
open class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        //For Picker to avoid FIleUri Exposed Exception
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        //SharedPreferences Initialization
        Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(packageName)
                .setUseDefaultSharedPreference(true)
                .build()
    }
}