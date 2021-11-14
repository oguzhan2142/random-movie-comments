package com.oguzhan.moviecommentsapp.providers

import android.util.Log
import com.google.gson.GsonBuilder
import com.oguzhan.moviecommentsapp.model.Result
import com.oguzhan.moviecommentsapp.model.SearchPage
import com.oguzhan.moviecommentsapp.utils.Config
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request

private const val TAG = "MovieDbProvider"

class MovieDbProvider {


    val baseUrl =
        "https://api.themoviedb.org/3/search/movie?api_key=ee637fe7f604d38049e71cb513a8a04d&language=tr-TR"

    val client = OkHttpClient()

    suspend fun searchMovie(query: String): List<Result> = coroutineScope {

        withContext(Dispatchers.IO) {
            val httpUrl: HttpUrl = HttpUrl.Builder().apply {
                scheme("https")
                host("api.themoviedb.org")
                addPathSegment("3")
                addPathSegment("search")
                addPathSegment("movie")
                addQueryParameter("api_key", Config.MOVIE_DB_API_KEY)
                addQueryParameter("query", query)

            }.build()

            val request = Request.Builder().url(httpUrl).build()
            val response = client.newCall(request).execute()


            val body = response.body()?.string()


            val gson = GsonBuilder().create()
            val searchPage = gson.fromJson(body, SearchPage::class.java)



            val results = searchPage.results

            results



        }


    }


}