package com.example.superheroesapp.data

import com.google.gson.annotations.SerializedName

data class SuperheroResponse (
    @SerializedName("response") val response: String,
    @SerializedName("result-for")val resultsFor: String,
    @SerializedName("results")val results: List<Superhero>
){
    data class Superhero (
        @SerializedName("id") val id:Int,
        @SerializedName("name") val name:String,
        @SerializedName("biography") val biography:Biography,
        @SerializedName("image") val image: Image){
    }

    data class Biography(
        @SerializedName("full-name") val realName:String,
    ){}

    class Image (
        @SerializedName("url") val url:String
    ) { }
}