package com.boriskunda.bkapp.data

import com.google.gson.annotations.SerializedName

class Country(
    var nativeName: String,
    @SerializedName("name")
    var englishName: String,
    var area: String?,
    var borders: Array<String>
)