package com.oguzhan.moviecommentsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.oguzhan.moviecommentsapp.adapters.CommentsAdapter
import com.oguzhan.moviecommentsapp.model.Comment

const val UserCommentsActivity_COMMENTS = "UserCommentsActivity"

class UserCommentsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_comments)

        val recyclerView = findViewById<RecyclerView>(R.id.comments)
        val decorator = DividerItemDecoration(
            recyclerView.context,
            DividerItemDecoration.HORIZONTAL
        )


        val data = intent.getStringArrayListExtra(UserCommentsActivity_COMMENTS)
        val list = mutableListOf<Comment>()
        data?.forEach {
            val model = Gson().fromJson(it, Comment::class.java)
            list.add(model)
        }

        decorator.setDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.recycler_list_divider_black
            )!!
        )

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@UserCommentsActivity)
            isNestedScrollingEnabled = false
            addItemDecoration(decorator)
            adapter = CommentsAdapter(this@UserCommentsActivity, list)
        }
    }
}