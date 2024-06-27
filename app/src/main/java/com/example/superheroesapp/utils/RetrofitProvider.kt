package com.example.superheroesapp.utils

import com.example.superheroesapp.data.SuperheroApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitProvider {
    companion object{
        private fun getRetrofit(): Retrofit {
            /*val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)
            val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()*/
            return Retrofit.Builder()
                .baseUrl("https://superheroapi.com/api/7252591128153666/")
                //.client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun getSuperheroApiService():SuperheroApiService{
            return getRetrofit().create(SuperheroApiService::class.java)
        }

    }
}