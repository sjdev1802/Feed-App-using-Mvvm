package com.example.feedapp.models

import com.example.feedapp.utils.BaseModel
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FeedDataModel(
    @SerializedName("feedData")
    val feedList: FeedList?,
    @SerializedName("filtersData")
    val filtersData: FiltersList?
) : BaseModel()

@Parcelize
data class FiltersList(
    @SerializedName("contentList")
    val contentList: List<FilterModel>,
) : BaseModel()


@Parcelize
data class FeedList(
    @SerializedName("contentList")
    val contentList: List<ProductModel>,
    @SerializedName("next")
    val next: String? = null
) : BaseModel()
