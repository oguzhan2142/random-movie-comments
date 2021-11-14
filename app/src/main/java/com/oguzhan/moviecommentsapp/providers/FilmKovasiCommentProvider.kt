package com.oguzhan.moviecommentsapp.providers

import android.util.Log
import com.oguzhan.moviecommentsapp.model.Comment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.net.SocketException

private const val TAG = "FilmKovasiProvider"

class FilmKovasiCommentProvider : CommentProvider {


    val url = "https://filmkovasi.org"


    private fun commentsFromElement(doc: Document): MutableList<Comment> {
        val list = mutableListOf<Comment>()
        val ulTag = doc.getElementsByClass("sonyorumlar").first()

        ulTag?.children()?.forEach { child ->


            val aTag = child.getElementsByTag("a").first()

            val movieName = aTag?.getElementsByTag("span")?.first()?.text()
            val movieUrl = aTag?.attr("href")


            val content = child.getElementsByTag("span").last()?.text()


            if (movieName != null && content != null) {
                val comment = Comment(
                    source = url, content = content,
                    movieName
                    = movieName,
                    movieUrl = movieUrl,
                )

                list.add(comment)
            }

        }

        return list
    }


    override suspend fun getComments(): MutableList<Comment> = coroutineScope {

        withContext(Dispatchers.IO) {
            lateinit var doc: Document


            try {
                doc = Jsoup.connect(url).get()

            } catch (e: SocketException) {
                e.printStackTrace()
            }



            val models = commentsFromElement(doc)



            models

        }

    }
}