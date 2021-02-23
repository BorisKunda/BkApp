package com.boriskunda.bkapp.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.boriskunda.bkapp.data.Country
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class BkRepository private constructor(application: Application) {

    private val volleyRequestQueue: RequestQueue
    private val executor: ExecutorService
    val worldCountriesListMLd: MutableLiveData<List<Country>> = MutableLiveData()
    val borderCountriesListMLd: MutableLiveData<List<Country>> = MutableLiveData()

    init {
        volleyRequestQueue = Volley.newRequestQueue(application)
        executor = Executors.newSingleThreadExecutor()
    }

    companion object {

        fun getRepoInstance(application: Application): BkRepository {

            val instance: BkRepository by lazy { BkRepository(application) }

            return instance

        }

    }

    fun getCountriesList(urlName: String) {
        JsonArrayRequest(Request.Method.GET, urlName, null,

            {

            },

            {
//todo error flow
            }

        )
    }

}