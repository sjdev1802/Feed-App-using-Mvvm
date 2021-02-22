package com.example.feedapp.models

import com.example.feedapp.utils.BaseModel
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductModel(
    @SerializedName("productId")
    val productId: String,
    @SerializedName("imageUrl")
    val imageUrl: String?,
    @SerializedName("companyName")
    val companyName: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("founderProfile")
    val founderProfile: FounderProfile,
    @SerializedName("productType")
    var productType: ArrayList<Int>,
    @SerializedName("upvoteInfo")
    var upvoteInfo: UpvoteInfo,
) : BaseModel()

@Parcelize
data class UpvoteInfo(
    @SerializedName("upvotedByUser")
    val upvotedByUser: Boolean,
    @SerializedName("upvoteCount")
    val upvoteCount: Int
) : BaseModel()

@Parcelize
data class FounderProfile(
    @SerializedName("userId")
    val userId: String,
    @SerializedName("name")
    val name: String?,
    @SerializedName("imageUrl")
    val imageUrl: String?,
    @SerializedName("about")
    val about: String?
) : BaseModel()