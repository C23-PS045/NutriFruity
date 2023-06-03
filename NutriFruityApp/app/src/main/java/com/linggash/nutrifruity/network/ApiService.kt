package com.linggash.nutrifruity.network

import com.linggash.nutrifruity.model.Fruit
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("buah")
    suspend fun getFruit(): List<Fruit>
}