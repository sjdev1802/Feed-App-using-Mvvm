package com.example.feedapp

import com.example.feedapp.listeners.ApiListener
import com.example.feedapp.models.*
import com.example.feedapp.utils.Constants.Companion.PRODUCT_TYPE_BOOKMARK
import com.example.feedapp.utils.GsonParser
import com.example.feedapp.utils.TestDataUtils
import com.example.feedapp.utils.Utils
import kotlinx.coroutines.delay

class FeedRepository {

    private val DATA = "data"
    private val FEED_DATA = "feed_data"
    private val FILTERS_DATA = "filters_data"

    private var productList = ArrayList<ProductModel>()
    private var filteredList = ArrayList<ProductModel>()
    private var filters = ArrayList<FilterModel>()

    companion object {
        var repo: FeedRepository? = null
        fun getInstance(): FeedRepository {
            if (repo == null) repo = FeedRepository()
            return repo!!

        }
    }

    suspend fun fetchFeedData(listener: ApiListener) {
        listener.loading(true)
        delay(3000)

        val filtersList =
            GsonParser.parseJson(Utils.getSharedPreferences(FILTERS_DATA), FiltersList::class.java)
        val feedList =
            GsonParser.parseJson(Utils.getSharedPreferences(FEED_DATA), FeedList::class.java)

        val feedData: FeedDataModel?

        if (filtersList != null && feedList != null) {
            feedData =
                FeedDataModel(feedList, filtersList)
        } else {
            // adding dummy data for 1st time from testData.json and saving it in shared pref
            feedData = TestDataUtils.readJsonData(DATA, FeedDataModel::class.java)
            Utils.setSharedPreferences(FEED_DATA, feedData?.feedList?.toJson() ?: "")
            Utils.setSharedPreferences(FILTERS_DATA, feedData?.filtersData?.toJson() ?: "")
        }
        feedData?.feedList?.contentList?.apply {
            productList = this as ArrayList<ProductModel>
        }
        feedData?.filtersData?.contentList?.apply {
            filters = this as ArrayList<FilterModel>
        }
        filterFeed()
        listener.onSuccess(true, loading = false)

    }

    fun getLatestFeedData(): ArrayList<ProductModel> {
        return filteredList
    }

    fun getLatestFiltersData(): ArrayList<FilterModel> {
        return filters
    }

    suspend fun upvote(productModel: ProductModel, listener: ApiListener) {
        listener.loading(true)
        delay(1000)
        var success = false
        productList.map {
            if (it.productId == productModel.productId) {
                val upvotedByUser = productModel.upvoteInfo.upvotedByUser
                it.upvoteInfo =
                    UpvoteInfo(
                        !upvotedByUser,
                        if (upvotedByUser) it.upvoteInfo.upvoteCount - 1 else it.upvoteInfo.upvoteCount + 1
                    )
                success = true
            }
        }
        listener.onSuccess(success, false)
        filterFeed()
    }

    suspend fun filterClicked(
        selectedFilter: FilterModel,
        listener: ApiListener
    ) {
        listener.loading(true)
        delay(1000)
        getLatestFiltersData().map {
            if (it.id == selectedFilter.id) {
                it.selected = !it.selected
            }
            // remove else statement to make filters multi-select
            else {
                it.selected = false
            }
        }
        filterFeed()
        listener.onSuccess(true, loading = false)
        updateFiltersData()
    }


    private fun updateFiltersData() {
        FiltersList(getLatestFiltersData()).toJson()?.let {
            Utils.setSharedPreferences(
                FILTERS_DATA,
                it
            )
        }
    }

    suspend fun savePost(
        data: ProductModel?,
        listener: ApiListener
    ) {
        listener.loading(true)
        delay(1000)
        productList.map {

            if (it.productId == data?.productId) {
                if (!it.productType.contains(PRODUCT_TYPE_BOOKMARK))
                    it.productType.add(PRODUCT_TYPE_BOOKMARK)
                else it.productType = it.productType.filter {
                    it != PRODUCT_TYPE_BOOKMARK
                } as ArrayList<Int>
            }
        }
        filterFeed()
        listener.onSuccess(true, false)

    }

    private fun updateFeedData() {
        FeedList(productList, null).toJson()?.let {
            Utils.setSharedPreferences(
                FEED_DATA,
                it
            )
        }
    }

    private fun filterFeed() {
        productList.filter { checkInFilters(it) }.let {
            filteredList = it as ArrayList<ProductModel>
        }

        val tempList = ArrayList<ProductModel>()
        for (feedItem in productList) {
            if (checkInFilters(feedItem)) {
                tempList.add(feedItem)
            }
        }
        filteredList = tempList
        updateFeedData()
    }

    private fun checkInFilters(productModel: ProductModel): Boolean {
        val appliedFilters = getLatestFiltersData().filter { it.selected }
        if (appliedFilters.isEmpty()) return true

        var res = false
        for (currFilter in appliedFilters) {
            if (res) {
                break
            } else {
                res = productModel.productType.contains(currFilter.id)
            }

        }
        return res
    }

}