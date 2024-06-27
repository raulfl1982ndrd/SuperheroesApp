package com.example.superheroesapp.data

import com.google.gson.TypeAdapter
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

data class SuperheroResponse (
    @SerializedName("response") val response: String,
    @SerializedName("result-for")val resultsFor: String,
    @SerializedName("results")val results: List<Superhero>
){
    data class Superhero (
        @SerializedName("id") val id:Int,
        @SerializedName("name") val name:String,
        @SerializedName("powerstats") val stats:Stats,
        @SerializedName("biography") val biography:Biography,
        @SerializedName("work") val work:Work,
        @SerializedName("image") val image: Image){
    }
    class Stats (
        @JsonAdapter(IntegerAdapter::class) @SerializedName("intelligence") val intelligence: Int,
        @JsonAdapter(IntegerAdapter::class) @SerializedName("strength") val strength: Int,
        @JsonAdapter(IntegerAdapter::class) @SerializedName("speed") val speed: Int,
        @JsonAdapter(IntegerAdapter::class) @SerializedName("durability") val durability: Int,
        @JsonAdapter(IntegerAdapter::class) @SerializedName("power") val power: Int,
        @JsonAdapter(IntegerAdapter::class) @SerializedName("combat") val combat: Int,
    ) { }

    data class Biography(
        @SerializedName("full-name") val realName:String,
        @SerializedName("alter-egos") val alterEgos:String,
        @SerializedName("aliases") val aliases: List<String>,
        @SerializedName("place-of-birth") val placeOfBirth:String,
        @SerializedName("first-appearance") val firstApperance:String,
        @SerializedName("publisher") val publisher:String,
        @SerializedName("alignment") val alignment:String,
    ){}


    data class Work (
        @SerializedName("occupation") val occupation:String,
        @SerializedName("base") val base:String){
    }

    class Image (
        @SerializedName("url") val url:String
    ) { }


    class IntegerAdapter : TypeAdapter<Int>() {
        override fun write(out: JsonWriter?, value: Int) {
            out?.value(value)
        }

        override fun read(`in`: JsonReader?): Int {
            if (`in` != null) {
                val value: String = `in`.nextString()
                if (value != "null") {
                    return value.toInt()
                }
            }
            return 0
        }
    }
}

