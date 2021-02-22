package com.example.feedapp

import android.app.Application

class FeedApplication : Application() {

    companion object {
        var instance: FeedApplication? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}