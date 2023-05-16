package com.example.turmurom.database.models

import com.google.gson.annotations.SerializedName

data class ExampleAPIModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
//    val description: String,
//    val price: Int,
//    val discountPercentage: Float,
//    val rating: Float,
//    val stock: Float,
//    val brand: String,
//    val category: String,
//    val thumbnail: String,
//    val images: List<String>
//    @SerializedName("marks")
//    val marks: List<String>?
//    val marks: List<Mark>?
)
