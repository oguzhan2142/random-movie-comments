package com.oguzhan.moviecommentsapp.providers

import android.util.Log
import com.oguzhan.moviecommentsapp.model.Comment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import okhttp3.internal.Version.userAgent
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.lang.Exception
import java.security.Provider


private const val TAG = "JetFilmIzleProvider"

class JetFilmIzleProvider : CommentProvider {


    val source = "https://jetfilmizle.pro"
    val url = "https://jetfilmizle.pro/filmlere-son-yapilan-yorumlar"


    private fun commentsFromDoc(doc: Document): MutableList<Comment> {
        val comments = mutableListOf<Comment>()


        val timelineSection = doc.getElementsByClass("timeline-section").first()

        val firstRow = timelineSection?.getElementsByClass("row")?.first()


        firstRow?.children()?.forEach {

            val rootDiv = it.child(0)

            val aTag = rootDiv.child(0).getElementsByTag("a").first()

            val movieName = aTag?.text()

            val href = aTag?.attr("href")

            val contentDiv = rootDiv.child(1).getElementsByClass("box-item").first()

            val content = contentDiv?.text()


            val url = if (href != null) source + href else null
            if (movieName != null && content != null) {

                val model = Comment(source, content, movieName, url)

                comments.add(model)
            }

        }
        return comments
    }

    override suspend fun getComments(): MutableList<Comment> = coroutineScope {

        withContext(Dispatchers.IO) {
            lateinit var doc: Document

            try {
                doc = Jsoup.connect(url).userAgent("Mozilla").get()


            } catch (e: Exception) {

                e.printStackTrace()
            }


            val c = commentsFromDoc(doc)

            c.forEach { Log.d(TAG, "getComments: ${it.movieName}") }

            c

        }

    }
}