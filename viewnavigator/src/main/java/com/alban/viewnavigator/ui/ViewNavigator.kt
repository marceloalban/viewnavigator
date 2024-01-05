package com.alban.viewnavigator.ui

import android.animation.LayoutTransition
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import androidx.recyclerview.widget.LinearLayoutManager
import com.alban.viewnavigator.R
import com.alban.viewnavigator.databinding.ViewNavigatorFeatureInfoBinding
import com.alban.viewnavigator.databinding.ViewNavigatorFeatureMetricsBinding
import com.alban.viewnavigator.databinding.ViewNavigatorLayoutBinding
import com.alban.viewnavigator.graphic.BitmapCreator
import com.alban.viewnavigator.ui.adapter.ViewNavigatorAdapter
import com.alban.viewnavigator.util.DimensionUtil.convertPxToDpWithSufix
import com.alban.viewnavigator.util.DimensionUtil.convertSizeFromWidthAndHeight
import com.alban.viewnavigator.util.ImageViewUtil
import com.alban.viewnavigator.util.PopupUtil

class ViewNavigator @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    findViewLocationInWindow: Boolean = false,
) : LinearLayoutCompat(
    context, attrs, defStyleAttr
) {
    companion object {
        const val ANIMATION_TRANSITION_TIME = 250L
    }

    private val binding = ViewNavigatorLayoutBinding.inflate(
        LayoutInflater.from(context), this
    )

    private val bindingFeatureInfo = ViewNavigatorFeatureInfoBinding.inflate(
        LayoutInflater.from(context)
    )

    private val bindingFeatureMetrics = ViewNavigatorFeatureMetricsBinding.inflate(
        LayoutInflater.from(context)
    )

    private var onButtonCloseClickListener: OnClickListener? = null

    private val popupUtil = PopupUtil(findViewLocationInWindow)

    init {
        orientation = VERTICAL

        initViews()

        binding.layoutContent.layoutTransition = LayoutTransition().apply {
            setDuration(ANIMATION_TRANSITION_TIME)
            disableTransitionType(LayoutTransition.CHANGE_DISAPPEARING)
            disableTransitionType(LayoutTransition.CHANGE_APPEARING)
        }
    }

    private fun initViews() {
        binding.iconInformation.setOnClickListener {
            showFeature(bindingFeatureInfo.root)
        }

        binding.iconMetrics.setOnClickListener {
            showFeature(bindingFeatureMetrics.root)
        }

        binding.iconButtonClose.setOnClickListener {
            onButtonCloseClickListener?.onClick(it)
        }

        binding.iconExpandCollapse.setOnClickListener {
            binding.recyclerView.isVisible = !binding.recyclerView.isVisible

            binding.iconExpandCollapse.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    if (binding.recyclerView.isVisible) R.drawable.view_navigator_ic_up else R.drawable.view_navigator_ic_down
                )
            )
        }
    }

    fun setOnButtonCloseClickListener(
        onButtonCloseClickListener: OnClickListener,
    ) {
        this.onButtonCloseClickListener = onButtonCloseClickListener
    }

    private fun clearFeatures() {
        binding.layoutContent.removeAllViews()
    }

    private fun showFeature(view: View) {
        if (binding.layoutContent.childCount > 0) {
            val child = binding.layoutContent.getChildAt(0)
            clearFeatures()

            if (child == view) return
        }

        binding.layoutContent.addView(view)
    }

    fun setView(rootView: View) {
        binding.iconMetrics.isVisible = false
        clearFeatures()

        val adapter = ViewNavigatorAdapter(
            onClickListener = { view ->
                refreshFeatureMetrics(view)

                val imageView = ImageViewUtil(context).createImageViewFromBitmap(
                    BitmapCreator(view).createBitmap()
                )

                popupUtil.showPopup(view, imageView)
            }
        )

        adapter.setView(rootView)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
    }

    private fun refreshFeatureMetrics(view: View) {
        binding.iconMetrics.isVisible = true

        with(bindingFeatureMetrics) {
            txtMarginLeft.text = convertPxToDpWithSufix(context, view.marginLeft.toFloat())
            txtMarginTop.text = convertPxToDpWithSufix(context, view.marginTop.toFloat())
            txtMarginRight.text = convertPxToDpWithSufix(context, view.marginRight.toFloat())
            txtMarginBottom.text = convertPxToDpWithSufix(context, view.marginBottom.toFloat())
            txtPaddingLeft.text = convertPxToDpWithSufix(context, view.paddingLeft.toFloat())
            txtPaddingTop.text = convertPxToDpWithSufix(context, view.paddingTop.toFloat())
            txtPaddingRight.text = convertPxToDpWithSufix(context, view.paddingRight.toFloat())
            txtPaddingBottom.text = convertPxToDpWithSufix(context, view.paddingBottom.toFloat())
            txtSize.text = convertSizeFromWidthAndHeight(view.width, view.height)
        }
    }
}
