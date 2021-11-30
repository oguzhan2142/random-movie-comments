package com.oguzhan.moviecommentsapp.model
import com.google.gson.annotations.SerializedName

data class ImdbTop250MoviesResponse(
    @SerializedName("errorMessage")
    val errorMessage: String,
    @SerializedName("items")
    val items: List<Item>
)