package com.google.imagefinder.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.imagefinder.network.WikiData

/**
 * Date: 10-March-2020
 * Version: 1.0
 * A View model factory that provides Wiki Data(i.e. details of Image URLs) and application context to the Detail View Model.
 */
class DetailViewModelFactory(private val wikiData: WikiData, private val application: Application) :
    ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(wikiData, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}