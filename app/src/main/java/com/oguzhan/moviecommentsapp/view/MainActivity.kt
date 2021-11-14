package com.oguzhan.moviecommentsapp.view

import android.content.Context
import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oguzhan.moviecommentsapp.R
import com.oguzhan.moviecommentsapp.adapters.CommentsAdapter
import com.oguzhan.moviecommentsapp.viewmodel.CommentsViewModel
import com.oguzhan.moviecommentsapp.viewmodel.MoviedbViewModel
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {


    private val TAG = "MainActivity"

    lateinit var recyclerView: RecyclerView

    lateinit var commentsViewModel: CommentsViewModel
    lateinit var moviedbViewModel: MoviedbViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        commentsViewModel = ViewModelProvider(this).get(CommentsViewModel::class.java)
        moviedbViewModel = ViewModelProvider(this).get(MoviedbViewModel::class.java)

        recyclerView = findViewById(R.id.comments)

        registerForContextMenu(recyclerView)

        recyclerView.apply {
            layoutManager =  LinearLayoutManager(applicationContext)
            isNestedScrollingEnabled = false
        }


        commentsViewModel.comments.observe(this, Observer { comments ->
            Log.d(TAG, "onCreate: observed something")
            recyclerView.adapter = CommentsAdapter(this, comments)
        })


        lifecycleScope.launch {
            commentsViewModel.fetchComments()

            moviedbViewModel.search("lord of the rings")

        }


    }

    fun Context.isDarkThemeOn(): Boolean {
        return resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK == UI_MODE_NIGHT_YES
    }
}