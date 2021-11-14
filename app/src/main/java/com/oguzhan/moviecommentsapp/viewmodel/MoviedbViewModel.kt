package com.oguzhan.moviecommentsapp.viewmodel

import androidx.lifecycle.ViewModel
import com.oguzhan.moviecommentsapp.model.Result
import com.oguzhan.moviecommentsapp.providers.MovieDbProvider
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class MoviedbViewModel : ViewModel() {

    private val movieDbProvider = MovieDbProvider()


    suspend fun search(movieName: String): List<Result> = coroutineScope {


        val result = async { movieDbProvider.searchMovie(movieName) }
        result.await()

        listOf()

    }

}