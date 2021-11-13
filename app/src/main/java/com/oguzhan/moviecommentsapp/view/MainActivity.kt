package com.oguzhan.moviecommentsapp.view

import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.oguzhan.moviecommentsapp.R
import com.oguzhan.moviecommentsapp.adapters.CommentsAdapter
import com.oguzhan.moviecommentsapp.viewmodel.CommentsViewModel
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {


    private val TAG = "MainActivity"

    lateinit var recyclerView: RecyclerView

    lateinit var commentsViewModel: CommentsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.comments)

        registerForContextMenu(recyclerView)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(applicationContext)

            val divider =
                DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
            divider.setDrawable(
                ContextCompat.getDrawable(
                    baseContext,
                    R.drawable.recycler_list_divider
                )!!
            )
            addItemDecoration(divider)

        }

        commentsViewModel = ViewModelProvider(this).get(CommentsViewModel::class.java)

        commentsViewModel.comments.observe(this, Observer { comments ->
            Log.d(TAG, "onCreate: observed something")
            recyclerView.adapter = CommentsAdapter(this, comments)
        })


        lifecycleScope.launch {
            commentsViewModel.fetchComments()
        }

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {

            lifecycleScope.launch {
                commentsViewModel.fetchComments()
            }

        }


    }




}