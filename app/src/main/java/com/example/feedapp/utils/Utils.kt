package com.example.feedapp.utils

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.feedapp.FeedApplication

class Utils {
    companion object{
        private const val DATA_PREF = "dataPref"


        private fun getSharedPreferences(): SharedPreferences? {
            return FeedApplication.instance?.getSharedPreferences(
                DATA_PREF, MODE_PRIVATE)
        }

        fun getSharedPreferences(stringValue: String): String? {
            return getSharedPreferences()
                ?.getString(stringValue, "")
        }

        fun setSharedPreferences(key: String, value: String) {
            val pref: SharedPreferences? =
                getSharedPreferences()
            val editor = pref?.edit()
            editor?.putString(key, value)
            editor?.apply()
        }
    }
}