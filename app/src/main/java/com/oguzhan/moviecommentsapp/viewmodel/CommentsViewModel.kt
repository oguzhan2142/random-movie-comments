package com.oguzhan.moviecommentsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oguzhan.moviecommentsapp.model.Comment
import com.oguzhan.moviecommentsapp.providers.FilmKovasiCommentProvider
import com.oguzhan.moviecommentsapp.providers.FullHdFilmIzleCommentProvider
import com.oguzhan.moviecommentsapp.providers.SuperFullHdFilmIzleCommentProvider
import kotlinx.coroutines.*

class CommentsViewModel : ViewModel() {

    val comments = MutableLiveData<MutableList<Comment>>()
    private val fullHdFilmIzleProvider = FullHdFilmIzleCommentProvider()
    private val filmKovasiProvider = FilmKovasiCommentProvider()
    private val superFullHdFilmIzleProvider = SuperFullHdFilmIzleCommentProvider()



    suspend fun fetchComments() = coroutineScope {

        val fullHdFilmIzleComments = async { fullHdFilmIzleProvider.getComments() }
        val filmKovasiComments = async { filmKovasiProvider.getComments() }
        val superFullHdFilmIzleComments = async { superFullHdFilmIzleProvider.getComments() }


        val result = fullHdFilmIzleComments.await() + filmKovasiComments.await() + superFullHdFilmIzleComments.await()

        comments.value = result.toMutableList()


    }

}