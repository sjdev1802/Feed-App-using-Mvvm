package com.example.feedapp

import android.content.Intent
import android.os.Bundle
import com.example.feedapp.utils.BaseActivity

class MainActivity : BaseActivity() {

    private val activityNavigator by lazy {
        MainActivityNavigator(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }



    private fun handleIntent(intent: Intent?) {
        intent?.let {
            if (it.data?.pathSegments?.contains("product") == true){
                activityNavigator.openFeedFragment(intent.data?.getQueryParameter("id"))
            }else activityNavigator.openFeedFragment(null)

        }
    }
}