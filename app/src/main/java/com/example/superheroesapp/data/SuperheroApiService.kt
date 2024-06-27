package com.example.superheroesapp.data

import retrofit2.http.GET
import retrofit2.http.Path

interface SuperheroApiService {
    @GET("search/{name}")
    //suspend funcion que se llaman en segundo plano
    suspend fun findSuperheroesByName(@Path("name")query: String):SuperheroResponse
    @GET("{character-id}")
    suspend fun getSuperheroeById(@Path("character-id")id: Int): SuperheroResponse.Superhero
}