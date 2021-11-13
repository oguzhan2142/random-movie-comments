package com.oguzhan.moviecommentsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oguzhan.moviecommentsapp.model.Comment
import com.oguzhan.moviecommentsapp.providers.FilmKovasiProvider
import com.oguzhan.moviecommentsapp.providers.FullHdFilmIzleProvider
import kotlinx.coroutines.*

class CommentsViewModel : ViewModel() {

    val comments = MutableLiveData<MutableList<Comment>>()
    private val fullHdFilmIzleProvider = FullHdFilmIzleProvider()
    private val filmKovasiProvider = FilmKovasiProvider()


    suspend fun fetchComments() = coroutineScope {

        val fullHdFilmIzleComments = async { fullHdFilmIzleProvider.getComments() }
        val filmKovasiComments = async { filmKovasiProvider.getComments() }

        val result = fullHdFilmIzleComments.await() + filmKovasiComments.await()

        comments.value = result.toMutableList()


    }

}