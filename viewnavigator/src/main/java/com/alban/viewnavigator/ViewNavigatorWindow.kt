package com.alban.viewnavigator

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import androidx.appcompat.widget.LinearLayoutCompat
import com.alban.viewnavigator.ui.ViewNavigator

class ViewNavigatorWindow(
    private val rootView: View,
    findViewLocationInWindow: Boolean = false,
) {
    private val context = rootView.context

    private var windowManager: WindowManager? = null
    private var windowManagerParams: WindowManager.LayoutParams? = null

    private val windowWidth = context.resources.getDimensionPixelSize(
        R.dimen.view_navigator_window_width
    )

    private val viewNavigator = ViewNavigator(
        this.context, findViewLocationInWindow = findViewLocationInWindow
    ).apply {
        layoutParams = LinearLayoutCompat.LayoutParams(
            LinearLayoutCompat.LayoutParams.MATCH_PARENT,
            LinearLayoutCompat.LayoutParams.WRAP_CONTENT
        )
    }

    private fun checkPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(context)) {
                val dialog = AlertDialog.Builder(context)
                dialog.setTitle(context.getString(R.string.view_navigator_permission_title))
                dialog.setMessage(context.getString(R.string.view_navigator_permission_message))
                dialog.setPositiveButton(context.getString(R.string.view_navigator_permission_button)) { _, _ ->
                    val intent = Intent(
                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:${context.packageName}")
                    )
                    context.startActivity(intent)
                }
                dialog.show()
                false
            } else true
        } else true
    }

    @SuppressLint("ClickableViewAccessibility")
    fun show() {
        if (!checkPermission()) {
            return
        }

        setCurrentView(rootView)

        windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        val type = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            @Suppress("DEPRECATION")
            WindowManager.LayoutParams.TYPE_PHONE
        } else {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        }

        windowManagerParams = WindowManager.LayoutParams(
            windowWidth,
            WindowManager.LayoutParams.WRAP_CONTENT,
            type,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH or
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            PixelFormat.TRANSLUCENT
        )

        windowManagerParams?.let {
            it.gravity = Gravity.CENTER
        }

        windowManager?.addView(viewNavigator, windowManagerParams)

        var initialX = 0
        var initialY = 0
        var initialTouchX = 0f
        var initialTouchY = 0f

        viewNavigator.setOnButtonCloseClickListener {
            remove()
        }

        viewNavigator.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    initialX = windowManagerParams?.x ?: 0
                    initialY = windowManagerParams?.y ?: 0

                    initialTouchX = event.rawX
                    initialTouchY = event.rawY
                }
                MotionEvent.ACTION_MOVE -> {
                    windowManagerParams?.x = initialX + (event.rawX - initialTouchX).toInt()
                    windowManagerParams?.y = initialY + (event.rawY - initialTouchY).toInt()

                    windowManager?.updateViewLayout(viewNavigator, windowManagerParams)
                }
            }
            false
        }
    }

    fun setCurrentView(view: View) {
        viewNavigator.setView(view)
    }

    fun remove() {
        windowManager?.removeView(viewNavigator)
    }
}