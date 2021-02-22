package com.example.feedapp.listeners

import com.example.feedapp.FeedViewModel
import com.example.feedapp.models.FilterModel
import com.example.feedapp.models.ProductModel

class FeedViewListener(private val feedViewModel: FeedViewModel) : IFilterViewListener,
    IFeedItemViewListener {
    override fun filterClick(filterData: FilterModel) {
        feedViewModel.filterClicked(filterData)
    }

    override fun sharePressed(data: ProductModel?) {
        feedViewModel.sharePost(data)
    }

    override fun upvotePressed(data: ProductModel?) {
        feedViewModel.upvotePost(data)
    }

    override fun bookmarkPressed(data: ProductModel?) {
        feedViewModel.bookmarkPost(data)
    }


}