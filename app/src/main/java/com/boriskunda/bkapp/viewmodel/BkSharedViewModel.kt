package com.boriskunda.bkapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.boriskunda.bkapp.data.Country
import com.boriskunda.bkapp.repository.BkRepository
import com.boriskunda.bkapp.utils.CountryListSortOptions
import com.boriskunda.bkapp.utils.SingleLiveEvent

class BkSharedViewModel(application: Application) : AndroidViewModel(application) {

    val goToWorldCountriesFragmentSle: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val goToBorderCountriesFragmentSle: SingleLiveEvent<Boolean> = SingleLiveEvent()
    private var bkRepository: BkRepository = BkRepository.getRepoInstance(application)
    var worldCountriesListLd: LiveData<List<Country>>
    var borderCountriesListLd: LiveData<List<Country>>

    init {
        bkRepository.let {
            worldCountriesListLd = it.worldCountriesListMLd
            borderCountriesListLd = it.borderCountriesListMLd
        }
    }

    fun requestWorldCountriesList() {
        bkRepository.loadWorldCountriesList()
    }

    /**navigation*/
    fun openWorldCountriesScreen() {
        goToWorldCountriesFragmentSle.call()
    }

    fun openBorderCountriesScreen() {
        goToBorderCountriesFragmentSle.call()
    }

    fun sortCountriesListBy(countryListSortOptions: CountryListSortOptions) {
        bkRepository.performCountriesListSort(countryListSortOptions)
    }

}