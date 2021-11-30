package com.oguzhan.moviecommentsapp.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oguzhan.moviecommentsapp.model.Item
import com.oguzhan.moviecommentsapp.providers.ImdbProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext


class ImdbViewModel : ViewModel() {

    private val provider = ImdbProvider()
    var top250List = MutableLiveData<List<Item>>(null)


    suspend fun getImdbTop250() {


        withContext(Dispatchers.IO) {

            val movies = async { provider.getTop250() }
            val value = movies.await()?.items ?: emptyList()
            top250List.postValue(value)

        }


    }


}