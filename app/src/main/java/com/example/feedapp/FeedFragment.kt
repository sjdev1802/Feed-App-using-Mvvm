package com.example.feedapp

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.feedapp.adapters.FeedAdapter
import com.example.feedapp.adapters.FiltersAdapter
import com.example.feedapp.databinding.FeedFragmentBinding
import com.example.feedapp.listeners.FeedViewListener
import com.example.feedapp.models.FeedFragmentArgs
import com.example.feedapp.models.ProductModel
import com.example.feedapp.utils.BaseActivity
import com.example.feedapp.utils.BaseFragment

class FeedFragment : BaseFragment() {

    lateinit var binding: FeedFragmentBinding

    private val feedViewListener by lazy {
        FeedViewListener(feedViewModel, mainActivityNavigator)
    }
    private val mainActivityNavigator by lazy {
        MainActivityNavigator(activity as BaseActivity)
    }

    private lateinit var feedViewModel: FeedViewModel

    companion object {
        val ARGS = "ARGS"
        fun getInstance(feedFragmentArgs: FeedFragmentArgs?): FeedFragment {
            val bundle = Bundle()
            bundle.putParcelable(ARGS, feedFragmentArgs)
            val fragment = FeedFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FeedFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        feedViewModel = ViewModelProviders.of(this).get(FeedViewModel::class.java)
        setUpViews()
    }

    private fun setUpViews() {
        setUpFeedRecyclerView()
        setUpFilterRecyclerView()
        setUpObservers()
    }

    private fun setUpFilterRecyclerView() {
        binding.filtersRecyclerView.run {
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            val decorationFilterRecyclerView =
                DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL)
            decorationFilterRecyclerView.setDrawable(GradientDrawable().also {
                it.shape = GradientDrawable.RECTANGLE
                it.setSize(16, MATCH_PARENT)
            })
            addItemDecoration(decorationFilterRecyclerView)
            adapter =
                FiltersAdapter(feedViewListener)
        }
    }

    private fun setUpFeedRecyclerView() {
        val decorationFeedRecyclerView =
            DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        decorationFeedRecyclerView.setDrawable(GradientDrawable().also {
            it.shape = GradientDrawable.RECTANGLE
            it.setSize(MATCH_PARENT, 64)
        })

        binding.feedRecyclerView.run {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(decorationFeedRecyclerView)
            adapter = FeedAdapter(feedViewListener)
        }
    }

    private fun setUpObservers() {
        feedViewModel.getFeedData().observe(this,
            Observer<List<ProductModel>> {
                (binding.feedRecyclerView.adapter as FeedAdapter).run {
                    setItems(it)
                }
            })
        feedViewModel.getFilters().observe(this, Observer {
            (binding.filtersRecyclerView.adapter as FiltersAdapter).run {
                setItems(it)
            }
        })
        feedViewModel.getToastText().observe(this, Observer {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })
    }

}