package com.boriskunda.bkapp.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.boriskunda.bkapp.data.Country
import com.boriskunda.bkapp.manager.CoroutinesManager
import com.boriskunda.bkapp.utils.BkConstants
import com.boriskunda.bkapp.utils.CountryListSortOptions
import com.google.gson.Gson
import kotlinx.coroutines.Job
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch


class BkRepository private constructor(application: Application) {

    private val volleyRequestQueue: RequestQueue = Volley.newRequestQueue(application)
    val worldCountriesListMLd: MutableLiveData<List<Country>> = MutableLiveData()
    private var worldCountriesMutableList: MutableList<Country> = mutableListOf()
    val borderCountriesListMLd: MutableLiveData<List<Country>> = MutableLiveData()
    private lateinit var comparatorAreaAsc: Comparator<Country>
    private val gson: Gson = Gson()
    private val borderCountriesMutableList: MutableList<Country> = mutableListOf()


    init {
        setComparator()
    }

    companion object {

        fun getRepoInstance(application: Application): BkRepository {

            val instance: BkRepository by lazy { BkRepository(application) }

            return instance

        }

    }

    fun loadWorldCountriesList() {
        JsonArrayRequest(Request.Method.GET, BkConstants.GET_WORLD_COUNTRIES_LIST_URL, null,

            {
                Log.i(BkConstants.BK_LOG_TAG, "****---Response:$it---****")
                worldCountriesMutableList =
                    gson.fromJson(it.toString(), Array<Country>::class.java).toMutableList()
                worldCountriesListMLd.postValue(worldCountriesMutableList)
            },

            {
                Log.e(BkConstants.BK_LOG_TAG, "****---Response:${it.cause}---****")
            }

        ).let {
            volleyRequestQueue.add(it)
        }
    }

    fun loadBorderCountriesList(value: Country?) {

        var borderCountriesCodes: Array<String>? = value?.borders

        CoroutinesManager().ioScope.launch {
            val job = ArrayList<Job>()

            if (borderCountriesCodes != null) {
                for (code in borderCountriesCodes) {
                    job.add(launch {
                        loadCountryByItsCode(code)
                    })
                }
            }

            job.joinAll()
            borderCountriesListMLd.postValue(borderCountriesMutableList)
            Log.i(BkConstants.BK_LOG_TAG, "All Networks calls have completed executing")
        }
    }

    private fun loadCountryByItsCode(code: String) { //: // {

        var jsonObjectRequest = JsonObjectRequest(Request.Method.GET,
            BkConstants.GET_COUNTRY_BY_THREE_LETTER_CODE_URL + code,
            null,
            {
                Log.i(BkConstants.BK_LOG_TAG, "****---Response:$it---****")
                var country = gson.fromJson(it.toString(), Country::class.java)
                borderCountriesMutableList.add(country)
            },
            {
                Log.e(BkConstants.BK_LOG_TAG, "****---Response:${it.cause}---****")
            })

        volleyRequestQueue.add(jsonObjectRequest)

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
                worldCountriesMutableList.sortWith(comparatorAreaAsc)
                worldCountriesListMLd.postValue(worldCountriesMutableList)
            }

            CountryListSortOptions.AREA_DESC -> {
                worldCountriesMutableList =
                    worldCountriesMutableList.sortedWith(comparatorAreaAsc).reversed()
                        .toMutableList()
                worldCountriesListMLd.postValue(worldCountriesMutableList)
            }
        }

    }

    private fun setComparator() {
        comparatorAreaAsc = Comparator { a, b ->
            val e1: Double? = a.area?.toDoubleOrNull()
            val e2: Double? = b.area?.toDoubleOrNull()

            when {
                e1 == e2 -> {
                    0
                }
                e1 == null -> {
                    -1
                }
                e2 == null -> {
                    1
                }
                else -> if (e1 > e2) 1 else -1
            }
        }
    }


}

