package com.linggash.nutrifruity.model

import com.google.gson.annotations.SerializedName

data class FruitResponse(

    @field:SerializedName("id")
    val id: Long,

    @field:SerializedName("nama_buah")
    val name: String,

    @field:SerializedName("gambar")
    val photoUrl: String,
)

data class NutritionResponse(

    @field:SerializedName("id")
    val id: Long,

    @field:SerializedName("nutrisi")
    val nutrition: String,
)

data class BenefitResponse(

    @field:SerializedName("id")
    val id: Long,

    @field:SerializedName("manfaat")
    val benefit: String,
)

data class FruitDetailResponse(

    @field:SerializedName("id")
    val id: Long,

    @field:SerializedName("nama_buah")
    val name: String,

    @field:SerializedName("gambar")
    val photoUrl: String,

    @field:SerializedName("nutrisi")
    val nutritionResponse: List<NutritionResponse>,

    @field:SerializedName("manfaat")
    val benefitResponse: List<BenefitResponse>
)