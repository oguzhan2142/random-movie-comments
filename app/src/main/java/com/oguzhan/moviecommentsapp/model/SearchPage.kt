package com.oguzhan.moviecommentsapp.model

data class SearchPage(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)