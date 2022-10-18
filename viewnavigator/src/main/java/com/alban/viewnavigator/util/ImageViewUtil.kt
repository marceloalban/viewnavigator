package com.alban.viewnavigator.util

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import androidx.appcompat.widget.LinearLayoutCompat

class ImageViewUtil(
    private val context: Context,
) {
    fun createImageViewFromBitmap(bitmap: Bitmap?): ImageView {
        return ImageView(context).apply {
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
            )
            setImageBitmap(bitmap)
        }
    }
}
