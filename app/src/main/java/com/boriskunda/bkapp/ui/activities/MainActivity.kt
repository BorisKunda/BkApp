package com.boriskunda.bkapp.ui.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.boriskunda.bkapp.R
import com.boriskunda.bkapp.ui.fragments.BorderCountriesFragment
import com.boriskunda.bkapp.ui.fragments.WorldCountriesFragment
import com.boriskunda.bkapp.utils.BkConstants
import com.boriskunda.bkapp.utils.CountryListSortOptions
import com.boriskunda.bkapp.viewmodel.BkSharedViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var worldCountriesFragment: WorldCountriesFragment//blue
    private lateinit var borderCountriesFragment: BorderCountriesFragment//orange
    private lateinit var fragmentManager: FragmentManager
    private val bkSharedViewModel: BkSharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initFragments()
        observeVM()
        bkSharedViewModel.openWorldCountriesScreen()
    }

    private fun observeVM() {
        bkSharedViewModel.goToWorldCountriesFragmentSle.observe(this, {
            fragmentManager.beginTransaction().replace(R.id.fr_container_ll, worldCountriesFragment)
                .commit()
        })

        bkSharedViewModel.goToBorderCountriesFragmentSle.observe(this, {
            fragmentManager.beginTransaction().replace(R.id.fr_container_ll, borderCountriesFragment)
                .addToBackStack(
                    BkConstants.MAIN_FRAGMENT_CONTAINER_BACKSTACK
                ).commit()
        })
    }


    private fun initFragments() {
        fragmentManager = supportFragmentManager
        worldCountriesFragment = WorldCountriesFragment()
        borderCountriesFragment = BorderCountriesFragment()
    }

    /**menu*/
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.name_asc_menu_item -> {
                bkSharedViewModel.sortCountriesListBy(CountryListSortOptions.NAME_ASC)
            }

            R.id.name_desc_menu_item -> {
                bkSharedViewModel.sortCountriesListBy(CountryListSortOptions.NAME_DESC)
            }

            R.id.area_small_big_menu_item -> {
                bkSharedViewModel.sortCountriesListBy(CountryListSortOptions.AREA_ASC)
            }

            R.id.area_big_small_menu_item -> {
                bkSharedViewModel.sortCountriesListBy(CountryListSortOptions.AREA_DESC)
            }

        }

        return super.onOptionsItemSelected(item)
    }


}