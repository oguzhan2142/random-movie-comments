package com.oguzhan.moviecommentsapp.model

data class Comment(
    val source: String,
    val content: String,
    val movieName: String,
    val movieUrl: String?,
)
