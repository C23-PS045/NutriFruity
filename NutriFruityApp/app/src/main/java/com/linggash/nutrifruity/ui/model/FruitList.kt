package com.linggash.nutrifruity.ui.model

import com.google.gson.annotations.SerializedName

data class FruitList(
    val list : List<Fruit>
)

data class Fruit(

    @field:SerializedName("id")
    val id: Long,

    @field:SerializedName("nama_buah")
    val name: String,

    @field:SerializedName("gambar")
    val photoUrl: String,

    @field:SerializedName("kandungan")
    val nutrition: List<Nutrition>,

    @field:SerializedName("manfaat")
    val benefit: List<Benefit>

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
    val nutrition: Long,
)