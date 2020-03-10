package com.google.imagefinder.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Date: 10-March-2020
 * Version: 1.0
 * A simple data class for holding image URLs information extracted from API response.
 * A list of WikiData is passed to Recycler View.
 */

@Parcelize
data class WikiData(
    var source: String
): Parcelable