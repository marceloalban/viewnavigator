package com.alban.viewnavigator.graphic

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.applyCanvas
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import com.alban.viewnavigator.R
import com.alban.viewnavigator.util.DimensionUtil

class BitmapCreator(
    private val rootView: View,
) {
    companion object {
        const val TEXT_SIZE = 20f
    }

    private val textPaint = createPaint(android.R.color.black)

    private fun getColor(
        @ColorRes color: Int,
    ): Int = ContextCompat.getColor(rootView.context, color)

    private fun createPaint(
        @ColorRes colorRes: Int,
    ) = Paint().apply {
        isAntiAlias = true
        color = getColor(colorRes)
        textSize = TEXT_SIZE
    }

    private fun getTextAndBounds(
        marginOrPadding: Int,
        callback: (text: String, bounds: Rect) -> Unit,
    ) {
        if (marginOrPadding > 0) {
            val text = DimensionUtil.convertPxToDpWithSufix(
                rootView.context,
                marginOrPadding.toFloat()
            )
            val bounds = Rect()
            textPaint.getTextBounds(text, 0, text.length, bounds)

            callback(text, bounds)
        }
    }

    private fun drawMargins(
        anchorRect: RectF,
        canvas: Canvas,
    ) {
        getTextAndBounds(rootView.marginLeft) { text, bounds ->
            canvas.drawText(
                text,
                0f,
                anchorRect.centerY() + bounds.height() / 2,
                textPaint
            )
        }

        getTextAndBounds(rootView.marginTop) { text, bounds ->
            canvas.drawText(
                text,
                anchorRect.centerX() - bounds.width() / 2,
                bounds.height().toFloat(),
                textPaint
            )
        }

        getTextAndBounds(rootView.marginRight) { text, bounds ->
            canvas.drawText(
                text,
                anchorRect.width() - bounds.width(),
                anchorRect.centerY() + bounds.height() / 2,
                textPaint
            )
        }

        getTextAndBounds(rootView.marginBottom) { text, bounds ->
            canvas.drawText(
                text,
                anchorRect.centerX() - bounds.width() / 2,
                anchorRect.height(),
                textPaint
            )
        }
    }

    private fun drawPaddings(
        anchorRect: RectF,
        canvas: Canvas,
    ) {
        getTextAndBounds(rootView.paddingLeft) { text, bounds ->
            canvas.drawText(
                text,
                anchorRect.left,
                anchorRect.centerY() + bounds.height() / 2,
                textPaint
            )
        }

        getTextAndBounds(rootView.paddingTop) { text, bounds ->
            canvas.drawText(
                text,
                anchorRect.centerX() - bounds.width() / 2,
                anchorRect.top + bounds.height().toFloat(),
                textPaint
            )
        }

        getTextAndBounds(rootView.paddingRight) { text, bounds ->
            canvas.drawText(
                text,
                anchorRect.right - bounds.width(),
                anchorRect.centerY() + bounds.height() / 2,
                textPaint
            )
        }

        getTextAndBounds(rootView.paddingBottom) { text, bounds ->
            canvas.drawText(
                text,
                anchorRect.centerX() - bounds.width() / 2,
                anchorRect.bottom,
                textPaint
            )
        }
    }

    fun createBitmap(): Bitmap? {
        if (rootView.width == 0 || rootView.height == 0) return null

        val width = rootView.measuredWidth + rootView.marginLeft + rootView.marginRight
        val height = rootView.measuredHeight + rootView.marginTop + rootView.marginBottom
        val marginLeft = rootView.marginLeft.toFloat()
        val marginTop = rootView.marginTop.toFloat()
        val marginRight = rootView.marginRight.toFloat()
        val marginBottom = rootView.marginBottom.toFloat()
        val paddingLeft = rootView.paddingLeft.toFloat()
        val paddingTop = rootView.paddingTop.toFloat()
        val paddingRight = rootView.paddingRight.toFloat()
        val paddingBottom = rootView.paddingBottom.toFloat()

        val marginPaint = createPaint(R.color.view_navigator_margin_color)
        val paddingPaint = createPaint(R.color.view_navigator_padding_color)
        val contentPaint = createPaint(R.color.view_navigator_content_color)

        val bitmap = Bitmap.createBitmap(
            width,
            height,
            Bitmap.Config.ARGB_8888
        ).applyCanvas {
            val rectMargin = RectF(
                0f,
                0f,
                width.toFloat(),
                height.toFloat()
            ).apply {
                drawRect(this, marginPaint)
            }

            val rectPadding = RectF(
                marginLeft,
                marginTop,
                width - marginRight,
                height - marginBottom
            ).apply {
                drawRect(this, paddingPaint)
            }

            RectF(
                marginLeft + paddingLeft,
                marginTop + paddingTop,
                width - marginRight - paddingRight,
                height - marginBottom - paddingBottom
            ).apply {
                drawRect(this, contentPaint)
            }

            drawMargins(rectMargin, this)
            drawPaddings(rectPadding, this)
        }

        return bitmap
    }
}
