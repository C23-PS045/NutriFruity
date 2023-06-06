package com.linggash.nutrifruity.model

import com.google.gson.annotations.SerializedName

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

    @field:SerializedName("nutrisi")
    val nutrition: String,
)

data class Benefit(

    @field:SerializedName("id")
    val id: Long,

    @field:SerializedName("manfaat")
    val benefit: String,
)

data class FruitDetail(

    @field:SerializedName("id")
    val id: Long,

    @field:SerializedName("nama_buah")
    val name: String,

    @field:SerializedName("gambar")
    val photoUrl: String,

    @field:SerializedName("nutrisi")
    val nutrition: List<Nutrition>,

    @field:SerializedName("manfaat")
    val benefit: List<Benefit>
)
