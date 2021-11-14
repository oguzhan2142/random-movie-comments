package com.oguzhan.moviecommentsapp.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mancj.materialsearchbar.MaterialSearchBar
import com.oguzhan.moviecommentsapp.R
import com.oguzhan.moviecommentsapp.adapters.CommentsAdapter
import com.oguzhan.moviecommentsapp.viewmodel.CommentsViewModel
import com.oguzhan.moviecommentsapp.viewmodel.MoviedbViewModel
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(), MaterialSearchBar.OnSearchActionListener {


    private val TAG = "MainActivity"

    lateinit var recyclerView: RecyclerView
    lateinit var searchBar: MaterialSearchBar

    lateinit var commentsViewModel: CommentsViewModel
    lateinit var moviedbViewModel: MoviedbViewModel

    lateinit var searchFragmentContainer: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        commentsViewModel = ViewModelProvider(this).get(CommentsViewModel::class.java)
        moviedbViewModel = ViewModelProvider(this).get(MoviedbViewModel::class.java)

        recyclerView = findViewById(R.id.comments)
        searchBar = findViewById(R.id.searchBar)

        searchFragmentContainer = findViewById(R.id.search_fragment)


//        searchFragmentContainer.visibility = View.GONE

        registerForContextMenu(recyclerView)

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
                    Log.d("LOG_TAG", javaClass.simpleName + " text changed " + searchBar.text)


                }

                override fun afterTextChanged(editable: Editable) {}
            })

        }


        recyclerView.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            isNestedScrollingEnabled = false
        }


        commentsViewModel.comments.observe(this, { comments ->
            Log.d(TAG, "onCreate: observed something")
            recyclerView.adapter = CommentsAdapter(this, comments)
        })


        lifecycleScope.launch {
            commentsViewModel.fetchComments()

        }


    }


    override fun onSearchStateChanged(enabled: Boolean) {
        searchFragmentContainer.visibility = if(enabled) View.VISIBLE else View.GONE
    }

    override fun onSearchConfirmed(text: CharSequence?) {
        if (text != null) {

            Log.d(TAG, "onSearchConfirmed: $text")
            lifecycleScope.launch {
                moviedbViewModel.search(text.toString())
            }


        }
    }

    override fun onButtonClicked(buttonCode: Int) {
        when (buttonCode) {

            MaterialSearchBar.BUTTON_SPEECH -> { }
            MaterialSearchBar.BUTTON_BACK -> searchBar.closeSearch()
        }


    }
}