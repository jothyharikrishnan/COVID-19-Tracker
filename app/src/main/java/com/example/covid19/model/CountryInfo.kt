package com.example.covid19.model

data class CountryInfo(
    val _id: Int,
    val flag: String,
    val iso2: String,
    val iso3: String,
    val lat: Int,
    val long: Int
)