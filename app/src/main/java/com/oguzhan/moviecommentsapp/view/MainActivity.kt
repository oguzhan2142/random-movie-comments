package com.oguzhan.moviecommentsapp.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getDrawable
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mancj.materialsearchbar.MaterialSearchBar
import com.oguzhan.moviecommentsapp.R
import com.oguzhan.moviecommentsapp.UserCommentsActivity
import com.oguzhan.moviecommentsapp.UserCommentsActivity_COMMENTS
import com.oguzhan.moviecommentsapp.adapters.CommentsAdapter
import com.oguzhan.moviecommentsapp.adapters.MoviesAdapter
import com.oguzhan.moviecommentsapp.viewmodel.CommentsViewModel
import com.oguzhan.moviecommentsapp.viewmodel.ImdbViewModel
import com.oguzhan.moviecommentsapp.viewmodel.MoviedbViewModel
import com.oguzhan.moviecommentsapp.viewmodel.SearchResultsViewModel
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(), MaterialSearchBar.OnSearchActionListener {


    private val TAG = "MainActivity"


    lateinit var searchBar: MaterialSearchBar

    lateinit var commentsViewModel: CommentsViewModel
    lateinit var moviedbViewModel: MoviedbViewModel
    lateinit var searchResultsViewModel: SearchResultsViewModel
    lateinit var imdbViewModel: ImdbViewModel

    lateinit var infoTextView: TextView

    lateinit var searchFragmentContainer: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        commentsViewModel = ViewModelProvider(this).get(CommentsViewModel::class.java)
        moviedbViewModel = ViewModelProvider(this).get(MoviedbViewModel::class.java)
        searchResultsViewModel = ViewModelProvider(this).get(SearchResultsViewModel::class.java)
        imdbViewModel = ViewModelProvider(this).get(ImdbViewModel::class.java)



        searchBar = findViewById(R.id.searchBar)
        infoTextView = findViewById(R.id.status_info)

        searchFragmentContainer = findViewById(R.id.search_fragment)

//        findViewById<Button>(R.id.comments_btn).setOnClickListener {
//            val data = commentsViewModel.getCommentsAsStringArray()
//            val intent = Intent(this, UserCommentsActivity::class.java)
//            intent.putStringArrayListExtra(UserCommentsActivity_COMMENTS, data)
//            startActivity(intent)
//        }

        searchBar.apply {
            setOnSearchActionListener(this@MainActivity)
            addTextChangeListener(object : TextWatcher {
                override fun beforeTextChanged(
                    charSequence: CharSequence,
                    i: Int,
                    i1: Int,
                    i2: Int
                ) {
                }

                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {


                }

                override fun afterTextChanged(editable: Editable) {}
            })

        }
        val recyclerView = findViewById<RecyclerView>(R.id.imdb_top25)

        findViewById<TextView>(R.id.top250_view_all).setOnClickListener {

            Log.d(TAG, "onCreate: view all clicked")
        }

        imdbViewModel.top250List.observe(this, {
            if (it != null) {

                Log.d(TAG, "onCreate: ${it.toString()}")
                recyclerView.adapter = MoviesAdapter(it, this)
            }
        })

        commentsViewModel.dataCameFromCache.observe(this, {
            infoTextView.visibility = if (it) View.VISIBLE else View.GONE
        })

        commentsViewModel.isError.observe(this, {
            if (it) {
                infoTextView.visibility = View.VISIBLE
                infoTextView.text = "Yorumlar alınırken bir hata meydana geldi"
            }
        })

        lifecycleScope.launch {
            commentsViewModel.fetchComments()
            imdbViewModel.getImdbTop250()
        }

    }

    override fun onSearchStateChanged(enabled: Boolean) {
        searchResultsViewModel.searchResults.clear()
        searchFragmentContainer.visibility = if (enabled) View.VISIBLE else View.GONE
    }

    override fun onSearchConfirmed(text: CharSequence?) {
        searchBar.clearFocus()
        if (text != null) {

            lifecycleScope.launch {
                moviedbViewModel.search(text.toString())
            }
        }
    }

    override fun onButtonClicked(buttonCode: Int) {
        when (buttonCode) {
            MaterialSearchBar.BUTTON_SPEECH -> {}
            MaterialSearchBar.BUTTON_BACK -> searchBar.closeSearch()
        }
    }
}