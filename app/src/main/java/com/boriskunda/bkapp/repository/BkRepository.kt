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
import com.google.gson.Gson


class BkRepository private constructor(application: Application) {

    private val volleyRequestQueue: RequestQueue = Volley.newRequestQueue(application)
    val worldCountriesListMLd: MutableLiveData<List<Country>> = MutableLiveData()
    val borderCountriesListMLd: MutableLiveData<List<Country>> = MutableLiveData()

    companion object {

        fun getRepoInstance(application: Application): BkRepository {

            val instance: BkRepository by lazy { BkRepository(application) }

            return instance

        }

    }

    fun loadWorldCountriesListFromApi() {
        JsonArrayRequest(Request.Method.GET, BkConstants.GET_WORLD_COUNTRIES_LIST_URL, null,

            {
                Log.i("Bk", "Request Response:$it")
                val worldCountriesList = Gson().fromJson(it.toString(), Array<Country>::class.java).toList()
                worldCountriesListMLd.postValue(worldCountriesList)
            },

            {
                Log.e("Bk", "Request Response:${it.cause}")
            }

        ).let {
            volleyRequestQueue.add(it)
        }
    }

}