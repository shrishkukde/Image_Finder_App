package com.google.imagefinder.overview

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.imagefinder.R
import com.google.imagefinder.databinding.FragmentOverviewBinding

/**
 * Date: 10-March-2020
 * Version: 1.0
 * This Fragment consist of Grid View of Images and also contains navigation to Detail Fragment.
 */
class OverviewFragment : Fragment() {
    /**
     * Date: 10-March-2020
     * Lazily initialization of OverviewViewModel
     */
    private val viewModel: OverviewViewModel by lazy {
        ViewModelProviders.of(this).get(OverviewViewModel::class.java)
    }

    /**
     * Date: 10-March-2020
     * Inflating the layout with data binding
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentOverviewBinding.inflate(inflater)

        //Allows Data Binding to Observe LiveData
        binding.setLifecycleOwner(this)

        binding.photosGrid.adapter = PhotoGridAdapter(PhotoGridAdapter.OnClickListener {
            viewModel.displayPropertyDetails(it)
        })

        //Observer which will navigate to Detail Fragment with help of Nav Controller
        viewModel.navigateToSelectedProperty.observe(this, Observer {
            if (null != it) {
                this.findNavController().navigate(OverviewFragmentDirections.actionShowDetail(it))
                viewModel.displayPropertyDetailsComplete()
            }
        })

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        setHasOptionsMenu(true)
        return binding.root
    }

    /**
     * Date: 10-March-2020
     * A Search View, which takes keyword from Edit Text and then passed it to function getWikiImageProperties()
     * This keyword is appended to the query field "gpssearch" of WIKI API
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)

        val searchField = menu.findItem(R.id.search_menu)
        if (searchField != null) {
            val searchView = searchField.actionView as SearchView

            val editText =
                searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
            editText.hint = "Enter keyword to search"

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    //Passing the user input to View Model
                    viewModel.getWikiImageProperties(query.toString())
                    //Following two lines will hide the Keyboard.
                    val imm =
                        activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view!!.windowToken, 0)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }
            })
        }
        super.onCreateOptionsMenu(menu, inflater)
    }
}
