package com.baseutils.base

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.annotation.AnimRes
import android.support.annotation.DimenRes
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.transition.Fade
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.baseutils.transitions.DetailsTransition
import pub.devrel.easypermissions.EasyPermissions

/**
 * Created by Shivam Verma on 20/02/18.
 * Author: Shivam Verma
 * Project: KotlinBaseSample
 */


abstract class BaseActivity : AppCompatActivity(), OnFragmentInteractionListener {

    protected abstract val containerId: Int

    private lateinit var imm: InputMethodManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Activity", "OnCreate ${this::class.java.simpleName}")
        imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    }

    /**
     * Check If the device has permission
     */
    fun hasPermission(vararg permissions: String): Boolean {
        return EasyPermissions.hasPermissions(this, *permissions)
    }

    /**
     * Implement interface #EasyPermissions.PermissionCallbacks
     * to override the fun onPermissionsGranted and onPermissionsDenied
     * OR
     *
     * Use Annotation
     *     @AfterPermissionGranted(permissionRequestCode)
     */
    fun requestPermission(permissionRequestCode: Int, @StringRes message: Int, vararg permissions: String) {
        EasyPermissions.requestPermissions(
                this,
                getString(message),
                permissionRequestCode,
                *permissions)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }


    override fun onStart() {
        super.onStart()
        Log.d("Activity", "OnStart ${this::class.java.simpleName}")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Activity", "OnResume ${this::class.java.simpleName}")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Activity", "OnPause ${this::class.java.simpleName}")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Activity", "OnStop ${this::class.java.simpleName}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Activity", "OnDestroy ${this::class.java.simpleName}")
    }


    /**
     * Override Back Press For BackStack Fragment Handling
     */
    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            //super.onBackPressed();
            finish()
        }
    }

    /**
     * Use this function for simple one by one transaction with reverse
     *
     *
     * @addToBackStack : provide boolean to save the transaction in backstack
     *  on backPress the back stack pop these transaction in reverse
     *  send false if you do not want to add to backstack
     *  by default value is true so as to maintain back press
     *
     *  @extraTag if you want to open a new fragment(same fragment is already open) but with different values use it.
     *  Like in profile ifProfileA is open and you want ProfileB then add extraTag for it
     *
     *  @views list of views for shared Transition.Make sure these views should have transition Name
     *  Also the source view and destination view both should have transition name either in xml or in java
     *
     *
     *  @newFragmentEnterAnimation
     *  @currentFragmentExitAnimation
     *  @currentFragmentEnterAnimation
     *  @newFragmentExitAnimation
     *
     *  use these above animation to animate fragment enter and exit .
     *  Default animation already set
     *  if no animation required set value 0 in all
     */
    fun replaceFragment(fragmentClass: Class<*>, extraTag: String? = null, addToBackStack: Boolean = true, bundle: Bundle? = null, views: ArrayList<View>? = null, @AnimRes newFragmentEnterAnimation: Int = 0, @AnimRes currentFragmentExitAnimation: Int = 0, @AnimRes currentFragmentEnterAnimation: Int = 0, @AnimRes newFragmentExitAnimation: Int = 0, replaceWithAnimation: Boolean = true) {
        if (containerId == 0) throw NullPointerException("containerId cannot be null")
        val finalTag = fragmentClass.simpleName + (extraTag ?: "")
        val isPopBackStack = supportFragmentManager.popBackStackImmediate(finalTag, 0)
        when {
            !isPopBackStack -> {
                val fragment = supportFragmentManager.findFragmentByTag(finalTag)
                        ?: fragmentClass.newInstance()
                when (fragment) {
                    is BaseFragment<*, *> -> {
                        if (bundle != null) fragment.bundle = bundle
                        // setDefaultFragmentBackGround(fragment)
                        val transaction = supportFragmentManager.beginTransaction()

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && views != null) {
                            fragment.sharedElementEnterTransition = DetailsTransition()
                            views.filter { ViewCompat.getTransitionName(it) != null }.forEach { v -> transaction.addSharedElement(v, ViewCompat.getTransitionName(v)) }
                            fragment.enterTransition = Fade()
                            currentFragment?.exitTransition = Fade()
                            fragment.sharedElementReturnTransition = DetailsTransition()
                        } else if (replaceWithAnimation)
                            transaction.setCustomAnimations(newFragmentEnterAnimation, currentFragmentExitAnimation, currentFragmentEnterAnimation, newFragmentExitAnimation)
//                        if (currentFragment == fragment && currentFragment?.tag == fragment.tag) return
                        transaction.replace(containerId, fragment, finalTag)
                        if (addToBackStack) transaction.addToBackStack(finalTag)
                        transaction.commit()
                        supportFragmentManager.executePendingTransactions()
                    }
                }
            }
        }
    }


    /**
     * Add Back Button To Toolbar with click @onBackPressed
     *
     * @icon : Provide Color Id in Resource
     */
    fun setToolbarWithBackButton(@DrawableRes icon: Int? = null) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        if (icon != null) supportActionBar?.setHomeAsUpIndicator(icon)
    }

    /**
     * Use it to show Keyboard
     */
    fun showKeyboard() {
        try {
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Use it to hide Keyboard
     * @editText provide if hide for particular editText
     */
    fun hideKeyboard(editText: EditText? = null) {
        if (editText != null && editText.windowToken != null)
            imm.hideSoftInputFromWindow(editText.windowToken, 0)
        if (currentFocus != null && currentFocus.windowToken != null)
            imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }

    /**
     * Log for printing(will be shown)
     */
    fun log(message: String?) {
        when {
            message != null -> Log.d("Log Print-->", message)
        }
    }

    private var toast: Toast? = null
    /**
     * showToast with message
     *  @view: for custom toast provide view perform inflater and other task at your end
     *  @gravity: provide gravity
     */
    fun showToast(message: String?, view: View? = null, duration: Int = Toast.LENGTH_LONG, gravity: Int = Gravity.BOTTOM or Gravity.CENTER_VERTICAL, @DimenRes x: Int = 0, @DimenRes y: Int = 0, cancelPrevious: Boolean = false) {
        if (cancelPrevious) toast?.cancel()

        // set custom view
        when {
            view != null -> {
                toast = Toast(applicationContext)
                toast?.view = view
            }
            else -> toast = Toast.makeText(applicationContext, message, duration)
        }
        // set duration
        toast?.duration = duration
        // set position
        var marginY = 0
        var marginX = 0
        if (y != 0) marginY = resources.getDimensionPixelSize(y)
        if (x != 0) marginX = resources.getDimensionPixelSize(x)
        if (marginX > 0 || marginY > 0) toast?.setGravity(gravity, marginX, marginY)
        // show toast
        toast?.show()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * remove all the fragment transactions from the backstack
     */
    fun clearBackStack() {
        val fragmentManager = supportFragmentManager
        //this will clear the back stack and displays no animation on the screen
        // fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        val backStackCount = fragmentManager.backStackEntryCount
        (0 until backStackCount)
                .map {
                    // Get the back stack fragment id.
                    fragmentManager.getBackStackEntryAt(it).id
                }
                .forEach { fragmentManager.popBackStack(it, FragmentManager.POP_BACK_STACK_INCLUSIVE) }
    }

    /**
     * Get the current fragment,it sometimes show incorrect when use with showFragment,
     * always works with addFragment
     */
    var currentFragment: BaseFragment<*, *>? = null
        get() {
            val frg = supportFragmentManager.findFragmentById(containerId)
            return when (frg) {
                null -> null
                else -> frg as BaseFragment<*, *>
            }
        }

}