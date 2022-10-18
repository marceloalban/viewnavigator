package com.alban.viewnavigator.util

import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupWindow
import androidx.core.view.marginLeft
import androidx.core.view.marginTop

class PopupUtil(
    private val findViewLocationInWindow: Boolean = false,
) {
    private var popupWindow: PopupWindow? = null

    fun showPopup(view: View, content: ImageView) {
        try {
            createPopup(view, content, view)
        } catch (e: Exception) {
            try {
                createPopup(view, content, content)
            } catch (e: Exception) {
                Log.wtf(PopupUtil::class.simpleName, e.message)
            }
        }
    }

    private fun createPopup(view: View, content: ImageView, anchor: View) {
        popupWindow?.dismiss()
        popupWindow = PopupWindow(
            content,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        popupWindow?.isOutsideTouchable = true
        popupWindow?.isFocusable = true
        popupWindow?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        popupWindow?.isClippingEnabled = false

        val locationArray = IntArray(2)

        if (findViewLocationInWindow) {
            view.getLocationInWindow(locationArray)
        } else {
            view.getLocationOnScreen(locationArray)
        }

        val location = Rect()
        location.left = locationArray[0]
        location.top = locationArray[1]

        popupWindow?.showAtLocation(
            anchor,
            Gravity.NO_GRAVITY,
            location.left - view.marginLeft,
            location.top - view.marginTop
        )
    }
}
