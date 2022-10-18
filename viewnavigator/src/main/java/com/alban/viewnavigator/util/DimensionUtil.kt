package com.alban.viewnavigator.util

import android.content.Context
import kotlin.math.roundToInt

object DimensionUtil {
    private const val DP = "dp"
    private const val PX = "px"

    fun convertSizeFromWidthAndHeight(width: Int, height: Int): String =
        "$width$PX x $height$PX"

    fun convertPxToDpWithSufix(context: Context, px: Float): String =
        convertPxToDp(context, px).toString().plus(DP)

    private fun convertPxToDp(context: Context, px: Float): Int =
        (px / context.resources.displayMetrics.density).roundToInt()
}
