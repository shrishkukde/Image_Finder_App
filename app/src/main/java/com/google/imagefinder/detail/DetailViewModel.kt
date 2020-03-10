package com.google.imagefinder.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.imagefinder.network.WikiData

/**
 * Date: 10-March-2020
 * Version: 1.0
 * Detail View Model for Detail Fragment. It contains information about the selected image.
 */
class DetailViewModel(@Suppress("UNUSED_PARAMETER") wikiData: WikiData, app: Application) :
    AndroidViewModel(app) {

    private val _selectedProperty = MutableLiveData<WikiData>()

    //Exposed live data for the selected image
    val selectedProperty: LiveData<WikiData>
        get() = _selectedProperty

    init {
        _selectedProperty.value = wikiData
    }
}