package com.oguzhan.moviecommentsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oguzhan.moviecommentsapp.model.movie_detail.MovieDetail
import com.oguzhan.moviecommentsapp.providers.MovieDbProvider
import kotlinx.coroutines.coroutineScope

class MovieDetailsViewModel : ViewModel(){
    private val movieDbProvider = MovieDbProvider()


    val movieDetail = MutableLiveData<MovieDetail>(null)

    suspend fun fetchModel(id :Int)= coroutineScope{

        val model = movieDbProvider.getMovieDetail(id)
        movieDetail.value = model

    }

}