package com.example.feedapp.utils

import com.example.feedapp.FeedFragment

fun BaseActivity.openFragment(baseFragment: FeedFragment, addToBackStack : Boolean = false) {
    val transaction = supportFragmentManager.beginTransaction()
    transaction.replace(android.R.id.content, baseFragment)
    if(addToBackStack) transaction.addToBackStack(null)
    transaction.commit()
}
