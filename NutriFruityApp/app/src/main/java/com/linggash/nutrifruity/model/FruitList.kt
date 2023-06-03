package com.linggash.nutrifruity.model

import com.google.gson.annotations.SerializedName

data class ListFruit(
    val fruitResponse: List<Fruit>
)

data class Fruit(

    @field:SerializedName("id")
    val id: Long,

    @field:SerializedName("nama_buah")
    val name: String,

    @field:SerializedName("gambar")
    val photoUrl: String,
)

data class Nutrition(

    @field:SerializedName("id")
    val id: Long,

    @field:SerializedName("kandungan")
    val nutrition: Long,
)

data class Benefit(

    @field:SerializedName("id")
    val id: Long,

    @field:SerializedName("manfaat")
    val benefit: Long,
)

data class FruitDetail(

    @field:SerializedName("id")
    val id: Long,

    @field:SerializedName("nama_buah")
    val name: String,

    @field:SerializedName("gambar")
    val photoUrl: String,
)
