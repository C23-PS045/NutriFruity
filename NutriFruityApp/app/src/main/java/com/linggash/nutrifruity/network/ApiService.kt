package com.linggash.nutrifruity.network

import com.linggash.nutrifruity.model.Fruit
import com.linggash.nutrifruity.model.FruitDetail
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("buah")
    suspend fun getFruit(): List<Fruit>

    @GET("buah/{fruitId}")
    suspend fun getFruitDetail(@Path("fruitId") fruitId: Long): FruitDetail
}