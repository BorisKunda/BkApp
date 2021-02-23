package com.boriskunda.bkapp.data

import com.google.gson.annotations.SerializedName

class Country(
    val nativeName: String,
    @SerializedName("name")
    val englishName: String,
    val area: String
    //borders
)