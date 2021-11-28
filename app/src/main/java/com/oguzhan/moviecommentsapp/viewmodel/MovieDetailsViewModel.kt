package com.oguzhan.moviecommentsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oguzhan.moviecommentsapp.model.movie_detail.Genre
import com.oguzhan.moviecommentsapp.model.movie_detail.MovieDetail
import com.oguzhan.moviecommentsapp.providers.MovieDbProvider
import kotlinx.coroutines.coroutineScope

class MovieDetailsViewModel : ViewModel() {
    private val movieDbProvider = MovieDbProvider()


    val movieDetail = MutableLiveData<MovieDetail>(null)

    suspend fun fetchModel(id: Int) = coroutineScope {

        val model = movieDbProvider.getMovieDetail(id)
        movieDetail.value = model

    }

    public fun getFormattedGenre(): String {

        val genres = movieDetail.value?.genres ?: return ""

        val delimitter = " • "
        val buffer = StringBuffer()

        for (i in genres.indices) {

            buffer.append(genres[i].name)
            if (i < genres.size - 1) {
                buffer.append(delimitter)
            }
        }

        return buffer.toString()
    }
}