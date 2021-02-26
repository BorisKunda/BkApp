package com.boriskunda.bkapp.utils

import android.util.Log

object BkConstants {
    const val MAIN_FRAGMENT_CONTAINER_BACKSTACK = "main_fragment_container_backstack"
    const val GET_WORLD_COUNTRIES_LIST_URL = "https://restcountries.eu/rest/v2/all"
    const val GET_COUNTRY_BY_THREE_LETTER_CODE_URL =
        "https://restcountries.eu/rest/v2/alpha/"//{code}
    const val BK_LOG_TAG = "[BK]"


    fun debugPrint(s: String) {
        Log.d("***DEBUG***", s)
    }

}
