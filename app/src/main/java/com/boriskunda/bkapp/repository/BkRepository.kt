package com.boriskunda.bkapp.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.boriskunda.bkapp.data.Country
import com.boriskunda.bkapp.utils.BkConstants
import com.boriskunda.bkapp.utils.CountryListSortOptions
import com.google.gson.Gson


class BkRepository private constructor(application: Application) {

    private val volleyRequestQueue: RequestQueue = Volley.newRequestQueue(application)
    val worldCountriesListMLd: MutableLiveData<List<Country>> = MutableLiveData()
    private var worldCountriesMutableList: MutableList<Country> = mutableListOf()
    val borderCountriesListMLd: MutableLiveData<List<Country>> = MutableLiveData()

    companion object {

        fun getRepoInstance(application: Application): BkRepository {

            val instance: BkRepository by lazy { BkRepository(application) }

            return instance

        }

    }

    fun loadWorldCountriesList() {
        JsonArrayRequest(Request.Method.GET, BkConstants.GET_WORLD_COUNTRIES_LIST_URL, null,

            {
                Log.i("Bk", "****---Response:$it---****")
                worldCountriesMutableList =
                    Gson().fromJson(it.toString(), Array<Country>::class.java).toMutableList()
                worldCountriesListMLd.postValue(worldCountriesMutableList)
            },

            {
                Log.e("Bk", "****---Response:${it.cause}---****")
            }

        ).let {
            volleyRequestQueue.add(it)
        }
    }

    fun performCountriesListSort(countryListSortOptions: CountryListSortOptions) {

        when (countryListSortOptions) {

            CountryListSortOptions.NAME_ASC -> {
                worldCountriesMutableList.apply { sortBy { it.englishName } }.let {
                    worldCountriesListMLd.postValue(it)
                }
            }

            CountryListSortOptions.NAME_DESC -> {
                worldCountriesMutableList.apply { sortByDescending { it.englishName } }.let {
                    worldCountriesListMLd.postValue(it)
                }
            }

            CountryListSortOptions.AREA_ASC -> {
                worldCountriesMutableList.apply { sortBy { it.area.toInt() } }.let {
                    worldCountriesListMLd.postValue(it)
                }
            }

            CountryListSortOptions.AREA_DESC -> {//int string conversion

                worldCountriesMutableList
                    .apply { sortByDescending { it.area.toInt() } }
                    .let {
                    worldCountriesListMLd.postValue(it)
                }
            }
        }

    }

}