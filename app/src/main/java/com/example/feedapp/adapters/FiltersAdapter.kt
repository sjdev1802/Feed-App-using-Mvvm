package com.example.feedapp.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.feedapp.listeners.IFilterViewListener
import com.example.feedapp.models.FilterModel
import com.example.feedapp.views.FilterView

class FiltersAdapter(private val filterViewListener: IFilterViewListener) :
    RecyclerView.Adapter<FilterItemVH>() {

    private var filtersList: List<FilterModel>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterItemVH {
        return FilterItemVH(FilterView(parent.context).also {
            it.setListener(filterViewListener)
        })
    }

    override fun getItemCount(): Int {
        return filtersList?.size ?: 0
    }

    override fun onBindViewHolder(holder: FilterItemVH, position: Int) {
        holder.updateView(filtersList?.get(position))
    }

    fun setItems(filters: List<FilterModel>?) {
        this.filtersList = filters
        notifyDataSetChanged()
    }

}

class FilterItemVH(val view: FilterView) : RecyclerView.ViewHolder(view) {
    fun updateView(data: FilterModel?) {
        view.populateData(data)
    }

}