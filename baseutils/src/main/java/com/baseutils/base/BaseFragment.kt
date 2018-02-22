package com.baseutils.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.AnimRes
import android.support.annotation.DimenRes
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.Toast
import pub.devrel.easypermissions.EasyPermissions

/**
 * Created by Shivam Verma on 29/08/17.
 * Author: Shivam Verma
 * Project: UtilsLibrary
 */
abstract class BaseFragment<DB : ViewDataBinding, VM : ViewModel> : Fragment() {

    protected abstract val fragmentId: Int
    protected abstract val viewModelClass: Class<VM>
    protected abstract val factory: ViewModelProvider.NewInstanceFactory?

    lateinit var binding: DB
    lateinit var viewModel: VM
    /**
     * To send Data From Fragment to its parent Activity
     */
    var mListener: OnFragmentInteractionListener? = null
    /**
     * Additional bundle to avoid setArgument(only once) issue in fragment
     */
    var bundle: Bundle? = null

    /**
     * Check If the device has permission
     */
    fun hasPermission(vararg permissions: String): Boolean {
        return EasyPermissions.hasPermissions(context!!, *permissions)
    }

    /**
     * Implement interface #EasyPermissions.PermissionCallbacks
     * to override the fun onPermissionsGranted and onPermissionsDenied
     * OR
     *
     * Use Annotation
     *     @AfterPermissionGranted(permissionRequestCode)
     */
    fun requestPermission(permissionRequestCode: Int, @StringRes message: Int = 0, vararg permissions: String) {
        EasyPermissions.requestPermissions(
                this,
                when (message) {
                    0 -> "Permission Required"
                    else -> getString(message)
                },
                permissionRequestCode,
                *permissions)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    /**
     * Call only one time when attach to activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        Log.d("Fragment", "Create ${this::class.java.simpleName}")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("Fragment", "OnCreateView ${this::class.java.simpleName}")
        binding = DataBindingUtil.inflate(inflater, fragmentId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Fragment", "View Created ${this::class.java.simpleName}")
        viewModel = when (factory) {
            null -> ViewModelProviders.of(this).get(viewModelClass)
            else -> ViewModelProviders.of(this, factory!!).get(viewModelClass)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("Fragment", "Activity Created ${this::class.java.simpleName}")
    }

    override fun onStart() {
        super.onStart()
        Log.d("Fragment", "Start ${this::class.java.simpleName}")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Fragment", "Stop ${this::class.java.simpleName}")
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        Log.d("Fragment", "Create Option Menu ${this::class.java.simpleName}")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Fragment", "Resume ${this::class.java.simpleName}")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Fragment", "Pause ${this::class.java.simpleName}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("Fragment", "Destroy View ${this::class.java.simpleName}")
    }

    /**
     * Call only one time when detach to activity
     */
    override fun onDestroy() {
        super.onDestroy()

        Log.d("Fragment", "Destroy ${this::class.java.simpleName}")
    }

    override fun onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu()
        Log.d("Fragment", "Destroy Option Menu ${this::class.java.simpleName}")
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        when (context) {
            is OnFragmentInteractionListener -> mListener = context
        }
        Log.d("Fragment", "Attach ${this::class.java.simpleName}")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("Fragment", "Detach ${this::class.java.simpleName}")
        mListener = null
    }

    fun log(message: String?) {
        (activity as? BaseActivity)?.log(message)
    }

    fun replaceFragment(fragmentClass: Class<*>, extraTag: String? = null, addToBackStack: Boolean = false, bundle: Bundle? = null, views: ArrayList<View>? = null, @AnimRes newFragmentEnterAnimation: Int = 0, @AnimRes currentFragmentExitAnimation: Int = 0, @AnimRes currentFragmentEnterAnimation: Int = 0, @AnimRes newFragmentExitAnimation: Int = 0) {
        (activity as? BaseActivity)?.replaceFragment(fragmentClass, extraTag, addToBackStack, bundle, views, newFragmentEnterAnimation, currentFragmentExitAnimation, currentFragmentEnterAnimation, newFragmentExitAnimation)
    }

    fun showToast(message: String?, view: View? = null, duration: Int = Toast.LENGTH_LONG, gravity: Int = Gravity.BOTTOM or Gravity.CENTER_VERTICAL, @DimenRes x: Int = 0, @DimenRes y: Int = 0, cancelPrevious: Boolean = false) {
        (activity as? BaseActivity)?.showToast(message, view, duration, gravity, x, y, cancelPrevious)
    }

    fun hideKeyboard(editText: EditText? = null) {
        (activity as? BaseActivity)?.hideKeyboard(editText)
    }

    fun clearBackStack() {
        (activity as? BaseActivity)?.clearBackStack()
    }
}