package com.oguzhan.moviecommentsapp.utils

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import com.google.gson.Gson
import com.oguzhan.moviecommentsapp.model.Comment

private const val COMMENTS_CACHE_KEY = "comments_cache"
private const val PREF_NAME = "com.oguzhan.app"

class CacheManager(context: Context) {
    var context: Context = context

    fun save(comments: List<Comment>) {

        val editor = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit()


        val list = mutableSetOf<String>()
        val gson = Gson()
        comments.forEach {
            list.add(gson.toJson(it))
        }

        editor.putStringSet(COMMENTS_CACHE_KEY, list)
        editor.apply()
        Log.d(TAG, "save: kaydedildi")
    }


    fun retrive(): List<Comment> {
        val list = mutableListOf<Comment>()


        val pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)

        val set = pref.getStringSet(COMMENTS_CACHE_KEY, setOf<String>())
        val gson = Gson()
        if (set != null) {
            set.forEach {
                list.add(gson.fromJson(it, Comment::class.java))
            }
        }


        Log.d(TAG, "retrive: ${list.size} kadar alindi")

        return list
    }


}