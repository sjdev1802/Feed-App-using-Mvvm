package com.example.feedapp.listeners

interface ApiListener {
    fun loading(isLoading: Boolean)
    fun onSuccess(resultSuccess: Boolean, loading: Boolean)
}
