package com.example.superheroesapp.data

import com.google.gson.annotations.SerializedName

data class SuperheroeResponse (
    @SerializedName("name") val name:String){
}