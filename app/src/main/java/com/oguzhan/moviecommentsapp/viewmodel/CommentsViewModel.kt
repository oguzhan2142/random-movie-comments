package com.oguzhan.moviecommentsapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oguzhan.moviecommentsapp.model.Comment
import com.oguzhan.moviecommentsapp.providers.FilmKovasiCommentProvider
import com.oguzhan.moviecommentsapp.providers.FullHdFilmIzleCommentProvider
import com.oguzhan.moviecommentsapp.providers.JetFilmIzleProvider
import com.oguzhan.moviecommentsapp.providers.SuperFullHdFilmIzleCommentProvider
import com.oguzhan.moviecommentsapp.utils.CacheManager
import kotlinx.coroutines.*
import java.lang.Exception

class CommentsViewModel(application: Application) : AndroidViewModel(application) {


    var dataCameFromCache = MutableLiveData(false)

    var manager: CacheManager = CacheManager(getApplication())

    val comments = MutableLiveData<MutableList<Comment>>()

    private val fullHdFilmIzleProvider = FullHdFilmIzleCommentProvider()
    private val filmKovasiProvider = FilmKovasiCommentProvider()
    private val superFullHdFilmIzleProvider = SuperFullHdFilmIzleCommentProvider()
    private val jetFilmIzleProvider = JetFilmIzleProvider()


    suspend fun fetchComments() = coroutineScope {
        comments.value = manager.retrive().toMutableList()

        try {

            val filmKovasiComments = async { filmKovasiProvider.getComments() }
            val fullHdFilmIzleComments = async { fullHdFilmIzleProvider.getComments() }
            val superFullHdFilmIzleComments = async { superFullHdFilmIzleProvider.getComments() }
            val jetFilmIzleComments = async { jetFilmIzleProvider.getComments() }
            val result =
                fullHdFilmIzleComments.await() + filmKovasiComments.await() + superFullHdFilmIzleComments.await() + jetFilmIzleComments.await()

            manager.save(result.toMutableList())
            comments.value = result.toMutableList()
            dataCameFromCache.value = false

        } catch (e: Exception) {
            if (!comments.value.isNullOrEmpty()) {
                dataCameFromCache.value = true
            }
        }


    }

}