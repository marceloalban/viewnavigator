package com.alban.viewnavigator.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.alban.viewnavigator.databinding.ViewNavigatorItemLayoutBinding

class ViewNavigatorAdapter(
    private val onClickListener: (view: View) -> Unit,
) : RecyclerView.Adapter<ViewNavigatorAdapter.ViewNavigatorViewHolder>() {

    companion object {
        const val ONE_CHILD = 1
        const val NO_CHILD = 0
    }

    private var currentView: View? = null
    private var rootView: View? = null
    private var selectedView: View? = null
    private var oldSelectedPosition: Int = -1

    @SuppressLint("NotifyDataSetChanged")
    fun setView(view: View) {
        currentView = view

        if (rootView == null) rootView = currentView

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewNavigatorViewHolder = ViewNavigatorViewHolder(
        ViewNavigatorItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewNavigatorViewHolder, position: Int) {
        currentView?.let {
            if (it is ViewGroup) {
                holder.bind(it.getChildAt(position))
            } else {
                holder.bind(it)
            }
        }
    }

    override fun getItemCount(): Int {
        return when (currentView) {
            null -> NO_CHILD
            is ViewGroup -> (currentView as ViewGroup).childCount
            else -> ONE_CHILD
        }
    }

    inner class ViewNavigatorViewHolder(
        private val binding: ViewNavigatorItemLayoutBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(view: View) {
            binding.txtName.text = view.javaClass.simpleName
            binding.iconNext.isVisible = view is ViewGroup
            binding.iconBack.isVisible = view != rootView
            binding.iconInvisible.isVisible = !view.isVisible
            binding.root.isSelected = view == selectedView

            binding.iconBack.setOnClickListener {
                setView(view.parent.parent as View)
            }

            binding.iconNext.setOnClickListener {
                setView(view)
            }

            binding.root.setOnClickListener {
                selectedView = view

                onClickListener(view)

                notifyItemChanged(adapterPosition)
                notifyItemChanged(oldSelectedPosition)

                oldSelectedPosition = adapterPosition
            }
        }
    }
}
