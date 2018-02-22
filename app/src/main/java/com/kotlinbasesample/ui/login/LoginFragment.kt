package com.kotlinbasesample.ui.login


import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v4.app.Fragment
import com.baseutils.base.BaseFragment
import com.kotlinbasesample.R
import com.kotlinbasesample.databinding.FragmentLoginBinding


/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {
    override val viewModelClass: Class<LoginViewModel>
        get() = LoginViewModel::class.java
    override val factory: ViewModelProvider.NewInstanceFactory?
        get() = null
    override val fragmentId: Int
        get() = R.layout.fragment_login

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel
    }

}
