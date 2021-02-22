package com.example.feedapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FeedViewModelFactory(private val mainActivityNavigator: MainActivityNavigator) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FeedViewModel(mainActivityNavigator) as T
    }

}