package com.alban.viewnavigator

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class ViewNavigatorRegister : Application.ActivityLifecycleCallbacks {
    companion object {
        fun ViewNavigatorRegister.register(
            application: Application,
            findViewLocationInWindow: Boolean = false,
        ) {
            this.findViewLocationInWindow = findViewLocationInWindow

            application.registerActivityLifecycleCallbacks(this)
        }
    }

    private var findViewLocationInWindow: Boolean = false
    private var activityStackCount: Int = 0
    private var viewNavigatorWindow: ViewNavigatorWindow? = null

    private fun refreshNavigator(activity: Activity) {
        val view = try {
            (activity.findViewById(android.R.id.content) as ViewGroup).getChildAt(0)
        } catch (e: Exception) {
            (activity.findViewById(android.R.id.content) as View)
        }

        view?.let {
            if (viewNavigatorWindow == null) {
                viewNavigatorWindow = ViewNavigatorWindow(view, findViewLocationInWindow)
                viewNavigatorWindow?.show()
            } else {
                viewNavigatorWindow?.setCurrentView(view)
            }
        }
    }

    private val fragmentLifecycleCallback = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentResumed(
            fragmentManager: FragmentManager,
            fragment: Fragment,
        ) {
            super.onFragmentResumed(fragmentManager, fragment)

            refreshNavigator(fragment.requireActivity())
        }
    }

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
        activityStackCount++

        if (activity is AppCompatActivity) {
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(
                fragmentLifecycleCallback, true
            )
        }
    }

    override fun onActivityDestroyed(activity: Activity) {
        activityStackCount--

        if (activity is AppCompatActivity) {
            if (activityStackCount == 0) {
                viewNavigatorWindow?.remove()
                viewNavigatorWindow = null
            }

            activity.supportFragmentManager.unregisterFragmentLifecycleCallbacks(
                fragmentLifecycleCallback
            )
        }
    }

    override fun onActivityResumed(activity: Activity) {
        refreshNavigator(activity)
    }

    override fun onActivityStarted(p0: Activity) {}
    override fun onActivityPaused(p0: Activity) {}
    override fun onActivityStopped(p0: Activity) {}
    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {}
}
