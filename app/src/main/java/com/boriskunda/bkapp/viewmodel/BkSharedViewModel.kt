package com.boriskunda.bkapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.boriskunda.bkapp.data.Country
import com.boriskunda.bkapp.repository.BkRepository
import com.boriskunda.bkapp.utils.CountryListSortOptions
import com.boriskunda.bkapp.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class BkSharedViewModel(application: Application) : AndroidViewModel(application) {

    val goToWorldCountriesFragmentSle: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val goToBorderCountriesFragmentSle: SingleLiveEvent<Boolean> = SingleLiveEvent()
    var borderCountriesListLd: LiveData<List<Country>>
    var selectedCountryMld: MutableLiveData<Country> = MutableLiveData()
    var worldCountriesListLd: LiveData<List<Country>>


    private var bkRepository: BkRepository = BkRepository.getRepoInstance(application)

    init {
        bkRepository.let {
            worldCountriesListLd = it.worldCountriesListMLd
            borderCountriesListLd = it.borderCountriesListMLd
        }
    }

    fun requestWorldCountriesList() {
        bkRepository.loadWorldCountriesList()
    }

    fun requestBorderCountriesList() {

        viewModelScope.launch {
            bkRepository.loadBorderCountriesList(selectedCountryMld.value)
        }

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