package com.example.feedapp.views

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.feedapp.R
import com.example.feedapp.utils.Constants.Companion.PRODUCT_TYPE_BOOKMARK
import com.example.feedapp.databinding.FeedItemViewBinding
import com.example.feedapp.listeners.IFeedActionItemListener
import com.example.feedapp.listeners.IFeedItemViewListener
import com.example.feedapp.models.ProductModel


class FeedItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var data: ProductModel? = null
    private var listener: IFeedItemViewListener? = null
    private val binding: FeedItemViewBinding =
        DataBindingUtil.inflate(LayoutInflater.from(context),
            R.layout.feed_item_view, this, true)

    init {
        binding.root.apply {
            layoutParams =
                LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )

            val shape = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                setColor(Color.WHITE)
                setStroke(3, Color.GRAY)
                cornerRadius = 4f;
            }
            background = shape
        }
    }

    fun setListener(feedItemViewListener: IFeedItemViewListener) {
        this.listener = feedItemViewListener
        binding.actionItems.setListener(object :
            IFeedActionItemListener {
            override fun sharePressed() {
                listener?.sharePressed(data)
            }

            override fun upvotePressed() {
                listener?.upvotePressed(data)
            }

            override fun savedPressed() {
                listener?.bookmarkPressed(data)
            }

        })
    }

    fun populateData(data: ProductModel?) {
        this.data = data
        binding.apply {
            data?.let {
                title.text = it.companyName
                desc.text = it.description

                val shape = GradientDrawable()
                shape.cornerRadius = 16f
                imageView.background = shape
                val requestOptions = RequestOptions().transform(RoundedCorners(16))
                Glide.with(context)
                    .load(data.imageUrl ?: "https://karostartup.com/wp-content/uploads/2020/10/Paytm-Founder-Karo-Startup-1024x512.jpg")
                    .apply(requestOptions)
                    .placeholder(R.drawable.ic_placeholder)
                    .dontAnimate()
                    .into(imageView)

                founderProfile.updateView(data.founderProfile)
                actionItems.updateView(data.upvoteInfo, data.productType.contains(PRODUCT_TYPE_BOOKMARK))
            }
        }
    }
}