package com.google.imagefinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI

/**
 * Date: 10-March-2020
 * Version: 1.0
 * MainActivity is only responsible for setting up the Navigation and also includes logic for "Up Button".
 * As a good practice, limited amount of logic is written here and rest implementation and responsibilities are divided into respective packages so as to the
 * principle of 'Separation of Concerns'
 *
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = this.findNavController(R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    /**
     * Date: 10-March-2020
     * In order to handle the 'Navigate Up' event from our activity, onSupportNavigateUp() method is used.
     *
     */
    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)
        return navController.navigateUp()
    }
}
