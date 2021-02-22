package com.example.feedapp.utils

import android.os.Parcelable


abstract class BaseModel : Parcelable {
    open fun toJson(): String? {
        return GsonParser.toJsonString(this)
    }
}
