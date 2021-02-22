package com.example.feedapp.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.feedapp.listeners.IFeedItemViewListener
import com.example.feedapp.models.ProductModel
import com.example.feedapp.views.FeedItemView

class FeedAdapter(private val feedItemViewListener: IFeedItemViewListener) : RecyclerView.Adapter<FeedItemVH>() {

    private var listItems: List<ProductModel>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedItemVH {
        return FeedItemVH(FeedItemView(parent.context))
    }

    override fun getItemCount(): Int {
        return listItems?.size ?: 0
    }

    override fun onBindViewHolder(holder: FeedItemVH, position: Int) {
        holder.setListener(feedItemViewListener)
        holder.updateView(listItems?.get(position))
    }

    fun setItems(list: List<ProductModel>?){
        this.listItems = list
        notifyDataSetChanged()
    }

}

class FeedItemVH(val view: FeedItemView) : RecyclerView.ViewHolder(view) {
    fun updateView(data: ProductModel?) {
        view.populateData(data)
    }

    fun setListener(feedItemViewListener: IFeedItemViewListener) {
        view.setListener(feedItemViewListener)
    }
}