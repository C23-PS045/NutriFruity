package com.linggash.nutrifruity.network

import com.linggash.nutrifruity.model.FruitResponse
import com.linggash.nutrifruity.model.FruitDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("buah")
    suspend fun getFruit(): List<FruitResponse>

    @GET("buah/{fruitId}")
    suspend fun getFruitDetail(@Path("fruitId") fruitId: Long): FruitDetailResponse
}