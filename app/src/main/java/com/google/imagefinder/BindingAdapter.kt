package com.google.imagefinder

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.imagefinder.network.WikiData
import com.google.imagefinder.overview.OverviewViewModel.*
import com.google.imagefinder.overview.PhotoGridAdapter

/**
 * Date: 10-March-2020
 * Version: 1.0
 * This adapter take a RecyclerView and object of List of Wiki Data(i.e. nothing but Image URLs) and initializes the Photo Grid Adapter with List Data.
 * This binding adapter will be executed when the XML item has "listData" attribute.
 */
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<WikiData>) {
    val adapter = recyclerView.adapter as PhotoGridAdapter
    adapter.submitList(data)
}

/**
 * Date: 10-March-2020
 * Used Glide library to load an image by URL into an Image View.
 * Glide helps in effectively and efficiently loading the images from the network.
 * It also contains loading and broken animations, in case of 'No Network' and 'Long Loading sessions'.
 * This binding adapter will be executed when the XML item has "imageUrl" attribute.
 */
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(imgView)
    }
}