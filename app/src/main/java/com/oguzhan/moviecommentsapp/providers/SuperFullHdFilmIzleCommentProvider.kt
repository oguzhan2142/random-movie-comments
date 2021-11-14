package com.oguzhan.moviecommentsapp.providers

import com.oguzhan.moviecommentsapp.model.Comment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.net.SocketException

class SuperFullHdFilmIzleCommentProvider : CommentProvider {


    val source = "https://superfullhdfilmizle.com"


    private fun getCommentsFromDocument(doc: Document): MutableList<Comment> {
        val list = mutableListOf<Comment>()


        val ulTag =
            doc.select("#wrap > div > div > div > div.home-new-2 > div:nth-child(3) > div.sidebar-comments > ul")
                .first()

        ulTag?.children()?.forEach { li ->

            val rootDiv = li.child(0)

            val aTag = rootDiv.child(0).child(0)

            val movieUrl = aTag.attr("href")

            val movieName = aTag.text()


            val contentDiv = rootDiv.child(1)
            contentDiv.children().forEach {
                val tagName = it.tag().name

                if (tagName == "span") {
                    it.remove()
                }
            }


            val content = contentDiv.text()


            val model = Comment(source, content, movieName, movieUrl)
            list.add(model)

        }


        return list
    }

    override suspend fun getComments(): MutableList<Comment> = coroutineScope {

        withContext(Dispatchers.IO) {
            val url = "https://superfullhdfilmizle.com/film/"
            lateinit var doc: Document


            try {
                doc = Jsoup.connect(url).get()

            } catch (e: SocketException) {
                e.printStackTrace()
            }

            val comments = getCommentsFromDocument(doc)

            comments


        }

    }
}