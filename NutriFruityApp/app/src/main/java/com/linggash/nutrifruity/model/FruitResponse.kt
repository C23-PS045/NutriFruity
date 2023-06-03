package com.linggash.nutrifruity.model

import com.google.gson.annotations.SerializedName

data class FruitResponse(

	@field:SerializedName("FruitResponse")
	val fruitResponse: List<FruitResponseItem>
)

data class FruitResponseItem(

	@field:SerializedName("nama_buah")
	val namaBuah: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("gambar")
	val gambar: String
)
