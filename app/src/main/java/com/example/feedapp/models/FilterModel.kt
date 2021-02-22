package com.example.feedapp.models

import com.example.feedapp.utils.BaseModel
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilterModel(
    @SerializedName("name")
    val name: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("selected")
    var selected: Boolean = false
): BaseModel()