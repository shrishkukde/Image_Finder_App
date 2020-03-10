package com.google.imagefinder.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Date: 10-March-2020
 * Version: 1.0
 *All the network related operations are configured in this file.
 */

//Base URL of Wiki API
private const val BASE_URL = "https://en.wikipedia.org/"

/**
 * Date: 10-March-2020
 * Retrofit builder, to build a retrofit object using a scalar converter and Co-routine CallAdapter,
 * which will allow us to use retrofit in Co-routine scope without hampering the UI thread.
 */
private val retrofit = Retrofit.Builder()
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

/**
 * Date: 10-March-2020
 * A public interface containing a function which returns a Deferred list of string.
 * A Deferred is a kind of Co-routine job that can directly return a result
 */
interface WikiApiService {
    @GET()
    fun getProperties(@Url url: String):
            Deferred<String>
}

/**
 * Date: 10-March-2020
 * Retrofit create call is expensive, hence defined a public object.
 * A retrofit object retrofitService is lazily initialized using retrofit.create() method.
 */
object WikiApi {
    val retrofitService: WikiApiService by lazy { retrofit.create(WikiApiService::class.java) }
}