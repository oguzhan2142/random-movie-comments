package com.oguzhan.moviecommentsapp.view

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.bumptech.glide.request.target.Target
import com.oguzhan.moviecommentsapp.R
import com.oguzhan.moviecommentsapp.model.movie_detail.Genre
import com.oguzhan.moviecommentsapp.model.movie_detail.MovieDetail
import com.oguzhan.moviecommentsapp.utils.Config
import com.oguzhan.moviecommentsapp.viewmodel.MovieDetailsViewModel
import jp.wasabeef.blurry.Blurry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.w3c.dom.Text

private const val TAG = "MovieDetailsActivity"
const val MOVIE_DETAIL_ACTIVITY_BUNDLE_KEY = "movie_id"

class MovieDetailsActivity : AppCompatActivity() {


    private lateinit var blurryImageView: ImageView
    private lateinit var posterSmall: ImageView
    private lateinit var viewModel: MovieDetailsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        viewModel = ViewModelProvider(this).get(MovieDetailsViewModel::class.java)

        blurryImageView = findViewById(R.id.blurry_imageview)
        posterSmall = findViewById(R.id.poster_small)




        findViewById<ImageButton>(R.id.back_button).setOnClickListener {
            finish()
        }


        viewModel.movieDetail.observe(this, {
            Log.d(TAG, "onCreate: ${it?.title}")

            if (it != null) {
                setUpImageViews(it)
                findViewById<TextView>(R.id.movie_name).text = it.title
                findViewById<TextView>(R.id.time_text).text = "${it.runtime} dk"
                findViewById<TextView>(R.id.genre_text).text = viewModel.getFormattedGenre()
                findViewById<TextView>(R.id.tagline).text = it.tagline
                findViewById<TextView>(R.id.overview).text = it.overview
            }
        })

        val movieId = intent.getIntExtra(MOVIE_DETAIL_ACTIVITY_BUNDLE_KEY, -1)
        lifecycleScope.launch {
            if (movieId != -1) {
                viewModel.fetchModel(movieId)
            }
        }
    }


    private fun setUpImageViews(model: MovieDetail) {
        Glide.with(this@MovieDetailsActivity)

            .load("${Config.MOVIE_DB_IMAGE_BASE}${model.poster_path}")
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    p0: GlideException?,
                    p1: Any?,
                    p2: Target<Drawable>?,
                    p3: Boolean
                ): Boolean {
                    return false

                }

                override fun onResourceReady(
                    p0: Drawable?,
                    p1: Any?,
                    p2: Target<Drawable>?,
                    p3: DataSource?,
                    p4: Boolean
                ): Boolean {
                    Log.d(TAG, "onResourceReady: resource geldi")

                    runOnUiThread {
                        posterSmall.setImageDrawable(p0)

                        val bitmap = p0?.toBitmap()

                        Blurry.with(this@MovieDetailsActivity)
                            .sampling(8)
                            .from(bitmap)
                            .into(blurryImageView)

                    }
                    return false
                }
            }).submit()
    }

}