package com.example.feedapp.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import com.example.feedapp.R
import com.example.feedapp.databinding.FeedItemActionsBinding
import com.example.feedapp.listeners.IFeedActionItemListener
import com.example.feedapp.models.UpvoteInfo

class FeedItemActionsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private var listener: IFeedActionItemListener? = null

    private val binding: FeedItemActionsBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.feed_item_actions,
        this,
        true
    )

    init {
        orientation = HORIZONTAL
        layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        setPadding(16, 16, 64, 16)
    }

    fun setListener(listener: IFeedActionItemListener?) {
        this.listener = listener

        binding.apply {
            upvoteContainer.setOnClickListener { listener?.upvotePressed() }
            shareContainer.setOnClickListener { listener?.sharePressed() }
            saveBtn.setOnClickListener { listener?.savedPressed() }
        }
    }

    @SuppressLint("NewApi")
    fun updateView(upvoteInfo: UpvoteInfo, saved: Boolean) {
        binding.apply {
            upvoteCount.text = upvoteInfo.upvoteCount.toString()
            if (upvoteInfo.upvotedByUser) {
                upvoteContainer.background = GradientDrawable().apply {
                    shape = GradientDrawable.RECTANGLE
                    cornerRadius = 4f
                    setColor(context.getColor(R.color.gray_v1))
                }
            } else {
                upvoteContainer.background = context.getDrawable(R.drawable.ripple)
            }
            saveBtn.apply {
                setImageDrawable(context.getDrawable(if (saved) R.drawable.ic_bookmarked else R.drawable.ic_bookmark))
            }
        }
    }
}