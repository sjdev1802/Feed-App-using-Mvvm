package com.example.feedapp.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.feedapp.R
import com.example.feedapp.databinding.FilterViewBinding
import com.example.feedapp.listeners.IFilterViewListener
import com.example.feedapp.models.FilterModel

@SuppressLint("NewApi")
class FilterView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val binding: FilterViewBinding =
        DataBindingUtil.inflate(LayoutInflater.from(context),
            R.layout.filter_view, this, true)
    private lateinit var feedViewListener: IFilterViewListener

    init {
        binding.root.apply {
            layoutParams = LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setPadding(64, 16, 64, 16)
            background = context.getDrawable(R.drawable.ripple)
        }
    }

    fun setListener(listener: IFilterViewListener) {
        feedViewListener = listener
    }

    fun populateData(filterData: FilterModel?) {
        filterData?.let {
            binding.apply {
                filterItemText.text = it.name
                filterItemText.setTextColor(
                    ContextCompat.getColor(
                        context,
                        if (it.selected) R.color.white else R.color.black
                    )
                )

                filterItemText.background = GradientDrawable().also { drawable ->
                    drawable.shape = GradientDrawable.RECTANGLE
                    drawable.cornerRadius = 64f
                    drawable.setColor(
                        ContextCompat.getColor(
                            context,
                            if (it.selected) R.color.black else R.color.gray_v1
                        )
                    )
                }
            }
            setOnClickListener {
                feedViewListener.filterClick(filterData)
            }
        }
    }
}