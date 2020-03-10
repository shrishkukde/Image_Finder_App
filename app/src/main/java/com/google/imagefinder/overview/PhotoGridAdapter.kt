package com.google.imagefinder.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.imagefinder.databinding.GridViewBinding
import com.google.imagefinder.network.WikiData

/**
 * Date: 10-March-2020
 * Version: 1.0
 * This class PhotoGridAdapter extends RecyclerView ListAdapter along with DiffUtil Callback.
 * DiffUtil is a helper for Recycler View adapters that calculates changes in lists and minimizes modification.
 * We need to pass a list of Image Url from our data class Wiki Data, View Holder and implementation of DiffCallback
 */
class PhotoGridAdapter(val onClickListener: OnClickListener) :
    ListAdapter<WikiData, PhotoGridAdapter.WikiDataViewHolder>(DiffCallback) {
    /**
     * Date: 10-March-2020
     * View holder for PhotoGrid Adapter which extends RecyclerView.ViewHolder.
     * It takes a binding variable 'binding' which will help in binding the data to Grid layout.
     */
    class WikiDataViewHolder(private var binding: GridViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(WikiData: WikiData) {
            binding.property = WikiData
            // The below statement instructs the data binding to execute immediately so that RecyclerView can immediately size the views.
            binding.executePendingBindings()
        }
    }

    //Helps the Recyclerview to identify which items have changed when the list is updated.
    companion object DiffCallback : DiffUtil.ItemCallback<WikiData>() {
        override fun areItemsTheSame(oldItem: WikiData, newItem: WikiData): Boolean {
            // Kotlin referential equality check
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: WikiData, newItem: WikiData): Boolean {
            return oldItem.source == newItem.source
        }
    }

    //Creates new item views for displaying data.
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WikiDataViewHolder {
        return WikiDataViewHolder(GridViewBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: WikiDataViewHolder, position: Int) {
        val wikiData = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(wikiData)
        }
        holder.bind(wikiData)
    }

    /**
     * Date: 10-March-2020
     * This listener handles the clicks on the Recycler View Items. This is required to navigate from Overview Screen to Detail Screen.
     */
    class OnClickListener(val clickListener: (wikiData: WikiData) -> Unit) {
        fun onClick(wikiData: WikiData) = clickListener(wikiData)
    }
}
