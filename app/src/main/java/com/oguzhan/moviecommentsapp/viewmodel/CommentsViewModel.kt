package com.oguzhan.moviecommentsapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
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

    var cacheManager: CacheManager = CacheManager(getApplication())

    val comments = MutableLiveData<MutableList<Comment>>()
    var isError = MutableLiveData(false)

    private val fullHdFilmIzleProvider = FullHdFilmIzleCommentProvider()
    private val filmKovasiProvider = FilmKovasiCommentProvider()
    private val superFullHdFilmIzleProvider = SuperFullHdFilmIzleCommentProvider()
    private val jetFilmIzleProvider = JetFilmIzleProvider()


    fun getCommentsAsStringArray(): ArrayList<String> {

        val list = arrayListOf<String>()
        comments.value?.forEach {
            val str = Gson().toJson(it, Comment::class.java)
            list.add(str)
        }
        return list
    }

    suspend fun fetchComments() = coroutineScope {
        comments.value = cacheManager.retrive().toMutableList()

        try {

            val filmKovasiComments = async { filmKovasiProvider.getComments() }
            val fullHdFilmIzleComments = async { fullHdFilmIzleProvider.getComments() }
            val superFullHdFilmIzleComments = async { superFullHdFilmIzleProvider.getComments() }
            val jetFilmIzleComments = async { jetFilmIzleProvider.getComments() }
            val result =
                fullHdFilmIzleComments.await() + filmKovasiComments.await() + superFullHdFilmIzleComments.await() + jetFilmIzleComments.await()

            cacheManager.save(result.toMutableList())
            comments.value = result.toMutableList()
            dataCameFromCache.value = false

        } catch (e: Exception) {
            if (!comments.value.isNullOrEmpty()) {
                dataCameFromCache.value = true
            } else {
                isError.value = true
            }
        }


    }

}