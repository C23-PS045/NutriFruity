package com.linggash.nutrifruity.network

import com.linggash.nutrifruity.model.Fruit
import retrofit2.http.GET

interface ApiService {

    @GET("buah")
    suspend fun getFruit(): List<Fruit>
}