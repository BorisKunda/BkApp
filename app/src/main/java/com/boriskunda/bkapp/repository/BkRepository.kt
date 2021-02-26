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
        //"https://restcountries.eu/rest/v2/all"
        JsonArrayRequest(Request.Method.GET, BkConstants.GET_WORLD_COUNTRIES_LIST_URL, null,

            {
                Log.i(BkConstants.BK_LOG_TAG, "****---Response:$it---****")
                worldCountriesMutableList =
                    Gson().fromJson(it.toString(), Array<Country>::class.java).toMutableList()
                worldCountriesListMLd.postValue(worldCountriesMutableList)
            },

            {
                Log.e(BkConstants.BK_LOG_TAG, "****---Response:${it.cause}---****")
            }

        ).let {
            volleyRequestQueue.add(it)
        }
    }


//
//        val jsonObjectRequest: JsonObjectRequest = object : JsonObjectRequest(
//            Request.Method.GET,
//            BkConstants.GET_COUNTRY_BY_THREE_LETTER_CODE_URL + code,
//            null,
//            { countryFromCode = Gson().fromJson(it.toString(), Country::class.java) }, { countryFromCode = null })


    // return countryFromCode


    private fun loadCountryByItsCode(code: String) { //: // {

        var jsonObjectRequest = JsonObjectRequest(Request.Method.GET,
            BkConstants.GET_COUNTRY_BY_THREE_LETTER_CODE_URL + code,
            null,
            {},
            {})

//        //https://restcountries.eu/rest/v2/alpha/EGY
//
        //   var countryFromCode: Country? = null
//        var jsonObjectRequest = object :
        //      Request.Method.GET,
        //      BkConstants.GET_COUNTRY_BY_THREE_LETTER_CODE_URL + code,
        //      null,
        //      {
        //      },
        //      {
        //      }
        //  )
    }


    /**----------------------------------------------------------------------------*/

    /**coroutines test*/
    fun tryAsyncNetworkCall(value: Country?) {

        var borderCountriesCodes: Array<String>? = value?.borders

        //Log.i(BkConstants.BK_LOG_TAG, "-----Async network calls without error handling-----")
        CoroutinesManager().ioScope.launch {
            val job = ArrayList<Job>()

            if (borderCountriesCodes != null) {
                for (code in borderCountriesCodes) {
                    job.add(launch {

                    })
                }
            }
            //Log.i(BkConstants.BK_LOG_TAG, "Making 10 asynchronous network calls")
            // for (i in 0..10) {
            job.add(launch {
                //loadWorldCountriesList()
                // Log.i(BkConstants.BK_LOG_TAG, "Network Call ID: $i")
                //fetchDetailsRepo.fetchDetails()
            })
            //  }

            job.joinAll()
            Log.i(BkConstants.BK_LOG_TAG, "All Networks calls have completed executing")
        }
    }

    /**----------------------------------------------------------------------------*/

    fun loadBorderCountriesList() {
        //https://restcountries.eu/rest/v2/name/{name}
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

