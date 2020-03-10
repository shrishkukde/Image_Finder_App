package com.google.imagefinder.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.imagefinder.databinding.FragmentDetailBinding

/**
 * Date: 10-March-2020
 * Version: 1.0
 * Detail View Fragment that displays full screen image of the from the selected image.
 * It used Jetpack Navigation Safe Args to display the selected image
 */
class DetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        @Suppress("UNUSED_VARIABLE")
        val application = requireNotNull(activity).application

        val binding = FragmentDetailBinding.inflate(inflater)
        //Jetpack Navigation's SafeArgs
        val wikiData = DetailFragmentArgs.fromBundle(arguments!!).selectedProperty

        val viewModelFactory = DetailViewModelFactory(wikiData, application)
        binding.viewModel = ViewModelProviders.of(
            this, viewModelFactory
        ).get(DetailViewModel::class.java)

        binding.setLifecycleOwner(this)
        return binding.root
    }
}