package com.example.superheroesapp.data

import com.google.gson.annotations.SerializedName

data class SuperheroeListResponse (
    @SerializedName("response") val response: String,
    @SerializedName("result-for")val resultsFor: String,
    @SerializedName("results")val results: List<SuperheroResponse>
){

}