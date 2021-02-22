package com.example.feedapp

import android.content.Intent
import com.example.feedapp.models.FeedFragmentArgs
import com.example.feedapp.models.ProductModel
import com.example.feedapp.utils.BaseActivity
import com.example.feedapp.utils.openFragment

class MainActivityNavigator constructor(private val baseActivity: BaseActivity?) {
    fun openFeedFragment(queryParameter: String?) {
        val baseFragment = FeedFragment.getInstance(FeedFragmentArgs(queryParameter))
        baseActivity?.openFragment(baseFragment)
    }

    fun sharePost(data: ProductModel?) {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(
            Intent.EXTRA_TEXT,
            "Hey check out this product on https://www.feedapp.com/product?id=" + data?.productId
        )
        sendIntent.type = "text/plain"
        baseActivity?.startActivity(sendIntent)
    }
}

