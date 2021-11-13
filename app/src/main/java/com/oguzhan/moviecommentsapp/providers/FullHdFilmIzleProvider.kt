package com.oguzhan.moviecommentsapp.providers

import com.oguzhan.moviecommentsapp.model.Comment
import kotlinx.coroutines.*
import org.jsoup.Jsoup


private const val TAG = "FullHdFilmIzleProvider"


class FullHdFilmIzleProvider : Provider {

    private val source = "https://www.fullhdfilmizlesene.com"

    val url: String = "https://www.fullhdfilmizlesene.com/film"





    private fun commentsFromElement(element: org.jsoup.nodes.Element?): MutableList<Comment> {
        val comments: MutableList<Comment> = mutableListOf()
        if (element != null) {

            val liTags = element.getElementsByTag("li")
            liTags.forEach {

                val aTag = it.children().first()

                val title = aTag?.text()
                val url = aTag?.attr("href")

                val pTag = it.children()[1]
                val content = pTag.text()
                if (title != null) {
                    val comment =
                        Comment(
                            source = source,
                            content = content,
                            movieName = title,
                            movieUrl = url,
                        )
                    comments.add(comment)

                }

            }
        }

        return comments

    }

    override suspend fun getComments(): MutableList<Comment> = coroutineScope {

        withContext(
            Dispatchers.IO
        ) {
            val doc = Jsoup.connect(url).get()

            val element = doc.getElementsByClass("sidebar-yorum").first()
            val comments =
                commentsFromElement(element)
            comments
        }


    }
}