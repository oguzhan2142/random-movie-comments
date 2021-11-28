package com.oguzhan.moviecommentsapp.view

import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.oguzhan.moviecommentsapp.R
import com.oguzhan.moviecommentsapp.viewmodel.MovieDetailsViewModel
import kotlinx.coroutines.launch

private const val TAG = "MovieDetailsActivity"
const val MOVIE_DETAIL_ACTIVITY_BUNDLE_KEY = "movie_id"

class MovieDetailsActivity : AppCompatActivity() {


    private lateinit var blurryImageView: ImageView
    private lateinit var viewModel: MovieDetailsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        viewModel = ViewModelProvider(this).get(MovieDetailsViewModel::class.java)

        blurryImageView = findViewById(R.id.blurry_imageview)


        findViewById<ImageButton>(R.id.back_button).setOnClickListener {
            finish()
        }


        viewModel.movieDetail.observe(this,{
            Log.d(TAG, "onCreate: ${it?.title}")
        })

        val movieId = intent.getIntExtra(MOVIE_DETAIL_ACTIVITY_BUNDLE_KEY, -1)
        lifecycleScope.launch {
            if (movieId != -1) {
                viewModel.fetchModel(movieId)
            }
        }
    }

}