package com.example.feedapp.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.feedapp.R
import com.example.feedapp.databinding.ProfileViewBinding
import com.example.feedapp.models.FounderProfile

@SuppressLint("NewApi")
class FounderProfileView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    val binding: ProfileViewBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.profile_view, this, true
    )

    init {

        orientation = HORIZONTAL
        layoutParams =
            LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

        binding.apply {
            imageView.background = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                cornerRadius = 20f
            }

        }
    }

    fun updateView(founderProfile: FounderProfile?) {

        founderProfile?.apply {
            binding.founderName.text = name

            val requestOptions = RequestOptions().transform(RoundedCorners(16))
            Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_avatar)
                .apply(requestOptions)
                .into(binding.imageView)
        }
    }
}