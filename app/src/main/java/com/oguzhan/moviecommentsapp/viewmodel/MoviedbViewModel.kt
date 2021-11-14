package com.oguzhan.moviecommentsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oguzhan.moviecommentsapp.model.Result
import com.oguzhan.moviecommentsapp.providers.MovieDbProvider
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class MoviedbViewModel : ViewModel() {

    private val movieDbProvider = MovieDbProvider()
    val searchResults = MutableLiveData<List<Result>>(mutableListOf())



    suspend fun search(movieName: String) = coroutineScope {
        val result = async { movieDbProvider.searchMovie(movieName) }
        searchResults.value = result.await()
    }



}