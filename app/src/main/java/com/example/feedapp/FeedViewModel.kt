package com.example.feedapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.feedapp.listeners.ApiListener
import com.example.feedapp.models.FilterModel
import com.example.feedapp.models.ProductModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


@SuppressLint("NewApi")
class FeedViewModel : ViewModel() {

    private var feedList: MutableLiveData<List<ProductModel>> = MutableLiveData()
    private var filtersData: MutableLiveData<List<FilterModel>> = MutableLiveData()
    private var toastData: MutableLiveData<String> = MutableLiveData()
    private var apiLoading: MutableLiveData<Boolean> = MutableLiveData()

    private val feedRepository by lazy {
        FeedRepository.getInstance()
    }

    init {
        CoroutineScope(IO).launch {
            feedRepository.fetchFeedData(object :
                ApiListener {
                override fun loading(isLoading: Boolean) {
                    onSuccessResponse(false, loading = isLoading)
                }

                override fun onSuccess(resultSuccess: Boolean, loading: Boolean) {
                    onSuccessResponse(resultSuccess, loading)
                    updateFeed()
                }

            })
        }
    }

    private fun updateFeed() {
        updateFeedList()
        filtersData.postValue(feedRepository.getLatestFiltersData())
    }

    fun getFeedData() = feedList

    fun getFilters() = filtersData

    fun getToastText() = toastData

    fun filterClicked(selectedFilter: FilterModel) {
        CoroutineScope(IO).launch {
            feedRepository.filterClicked(selectedFilter, object :
                ApiListener {
                override fun loading(isLoading: Boolean) {
                    onSuccessResponse(false, isLoading)
                }

                override fun onSuccess(resultSuccess: Boolean, loading: Boolean) {
                    onSuccessResponse(resultSuccess, loading)
                    updateFeed()
                }

            })


        }
    }

    private fun showToast(successString: String) {
        toastData.postValue(successString)
    }

    fun upvotePost(data: ProductModel?) {
        CoroutineScope(IO).launch {
            data?.let {
                feedRepository.upvote(it, object :
                    ApiListener {
                    override fun loading(isLoading: Boolean) {
                        onSuccessResponse(false, loading = true)
                    }

                    override fun onSuccess(resultSuccess: Boolean, loading: Boolean) {
                        onSuccessResponse(resultSuccess, loading)
                        updateFeedList()

                    }
                })
            }
        }
    }

    private fun onSuccessResponse(resultSuccess: Boolean, loading: Boolean) {
        if (loading) {
            apiLoading.postValue(loading)
            showToast("Processing")
        } else {
            apiLoading.postValue(loading)
            showToast(if (resultSuccess) "Success" else "Failed")
        }

    }

    fun bookmarkPost(data: ProductModel?) {
        CoroutineScope(IO).launch {
            feedRepository.savePost(data, object :
                ApiListener {
                override fun loading(isLoading: Boolean) {
                    onSuccessResponse(false, isLoading)
                }

                override fun onSuccess(resultSuccess: Boolean, loading: Boolean) {
                    onSuccessResponse(true, loading)
                    updateFeedList()
                }

            })
        }
    }

    private fun updateFeedList() {
        feedList.postValue(feedRepository.getLatestFeedData())
    }

}