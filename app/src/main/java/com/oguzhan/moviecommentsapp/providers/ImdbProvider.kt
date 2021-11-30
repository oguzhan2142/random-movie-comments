package com.oguzhan.moviecommentsapp.providers



import com.google.gson.Gson
import com.oguzhan.moviecommentsapp.model.ImdbTop250MoviesResponse
import com.oguzhan.moviecommentsapp.utils.Config
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import okhttp3.*
import java.io.IOException

class ImdbProvider {
    private val client = OkHttpClient()

     fun getTop250(): ImdbTop250MoviesResponse? {

        val request = Request
            .Builder()
            .url("https://imdb-api.com/en/API/Top250Movies/${Config.IMDB_KEY}")
            .build()

        val response = client.newCall(request).execute()

        val mBody = response.body()?.string()

        val model = Gson().fromJson(mBody, ImdbTop250MoviesResponse::class.java)
        return model

    }


}