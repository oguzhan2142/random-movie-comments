package com.oguzhan.moviecommentsapp.providers

import com.oguzhan.moviecommentsapp.model.Comment
import kotlinx.coroutines.coroutineScope

interface Provider {


    suspend fun getComments(): MutableList<Comment>

}