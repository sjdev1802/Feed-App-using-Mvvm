package com.example.feedapp.models

import com.example.feedapp.utils.BaseModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FeedFragmentArgs(val productId: String?) : BaseModel()