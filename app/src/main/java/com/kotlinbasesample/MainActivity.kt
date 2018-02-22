package com.kotlinbasesample

import android.os.Bundle
import com.baseutils.base.BaseActivity

class MainActivity : BaseActivity() {

    override val containerId: Int
        get() = R.id.container_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

}
