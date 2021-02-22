package com.example.feedapp.listeners

import com.example.feedapp.models.ProductModel

interface IFeedItemViewListener {
    fun sharePressed(data: ProductModel?)
    fun upvotePressed(data: ProductModel?)
    fun bookmarkPressed(data: ProductModel?)
}
