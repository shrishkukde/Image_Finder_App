package com.google.imagefinder.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.imagefinder.network.WikiApi
import com.google.imagefinder.network.WikiData
import kotlinx.coroutines.*
import org.json.JSONObject

/**
 * Date: 10-March-2020
 * Version: 1.0
 * View Model attached to the Fragment. This consists of all the decision making logic and calculations.
 */
class OverviewViewModel : ViewModel() {
    /**
     * Date: 10-March-2020
     * Encapsulated Live Data Properties and their respective gettable version.
     */
    // This internal MutableLiveData String that stores the list of image URls so that they can be passed to Recycler View.
    private val _images = MutableLiveData<List<WikiData>>()

    val images: LiveData<List<WikiData>>
        get() = _images

    // This internal MutableLiveData String stores the most recent response from the API call
    private val _response = MutableLiveData<String>()

    // The external immutable LiveData for the response String
    val response: LiveData<String>
        get() = _response

    /**
     * Date: 10-March-2020
     * Declared variables for Co-routine Job and Scope with MAIN Dispatcher.
     * [MAIN Dispatcher is used as Retrofit performs all its operations o background thread]
     * These variables are used to launch a Co-routine scope where the long running API call/operations will be performed.
     */
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    // This will be called when View Model is created and initialized. It contains a dummy URL. This will be removed and is just for test purpose.
    init {
        _images.value =
            listOf(WikiData("https://upload.wikimedia.org/wikipedia/commons/thumb/a/a0/Litherlandwalnutridge.jpg/200px-Litherlandwalnutridge.jpg"))
    }

    /**
     * Date: 10-March-2020
     * The function getWikiImageProperties() is responsible for parsing the JSON response and then extract image URl and stores them in Array imageUrlArray.
     * And then updates the live data _images with final values so that images for those values can be displayed by adapters.
     */
    fun getWikiImageProperties(Query: String) {
        coroutineScope.launch {
            var getPropertiesDeferred = WikiApi.retrofitService.getProperties(
                "w/api.php?action=query" +
                        "&prop=pageimages" +
                        "&format=json" +
                        "&piprop=thumbnail" +
                        "&pithumbsize=1080" +
                        "&pilimit=50" +
                        "&generator=prefixsearch" +
                        "&gpssearch=" + Query
            )
            try {
                /**
                 * Date: 10-March-2020
                 * Raw response from API call is captured here which will go for further processing
                 * await() is a suspend function on Deferred
                 */
                _response.value = getPropertiesDeferred.await()
                //Parsing of JSON response String
                val jsonObject = JSONObject(_response.value.toString())
                val pages = jsonObject.getJSONObject("query").getJSONObject("pages")
                //Getting all the keys of pages in JSON response
                val keys: Iterator<String> = pages.keys()
                // An Array to hold Image URL data of type Wiki Data.
                var listVariable = ArrayList<WikiData>()
                //While loop is created to iterate over RAW JSON response via keys and export result one by one.
                while (keys.hasNext()) {
                    val key: String = keys.next()
                    val innerObject = pages.getJSONObject(key)
                    try {
                        val thumbnail = innerObject.getJSONObject("thumbnail")
                        var wikiData = WikiData("")
                        wikiData.source = thumbnail.getString("source")
                        listVariable.add(wikiData)
                        _images.value = listVariable
                    } catch (e: Exception) {
                        _response.value = "Failure: ${e.message}"
                    }
                }
            } catch (e: Exception) {
                _response.value = "Failure: ${e.message}"
            }
        }
    }

    /**
     * Date: 10-March-2020
     * The following live data properties and methods are used for navigating from Overview Fragment to Detail Fragment with the help of Live Data.
     */
    private val _navigateToSelectedProperty = MutableLiveData<WikiData>()

    val navigateToSelectedProperty: LiveData<WikiData>
        get() = _navigateToSelectedProperty

    fun displayPropertyDetails(wikiData: WikiData) {
        _navigateToSelectedProperty.value = wikiData
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }

    //Loading OR Parsing of data should be stopped when View Model is destroyed. Henc,e the Co-routine Job is cancelled here.
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}