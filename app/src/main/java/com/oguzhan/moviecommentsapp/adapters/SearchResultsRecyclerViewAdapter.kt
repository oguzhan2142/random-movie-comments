package com.oguzhan.moviecommentsapp.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.oguzhan.moviecommentsapp.R
import com.oguzhan.moviecommentsapp.databinding.FragmentSearchResultsBinding
import com.oguzhan.moviecommentsapp.model.Result
import com.oguzhan.moviecommentsapp.utils.Config
import com.oguzhan.moviecommentsapp.view.MOVIE_DETAIL_ACTIVITY_BUNDLE_KEY
import com.oguzhan.moviecommentsapp.view.MovieDetailsActivity


class SearchResultsRecyclerViewAdapter(
    private val context: Context,
    private val values: MutableList<Result>
) : RecyclerView.Adapter<SearchResultsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentSearchResultsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
//        holder.imageView = item.id
        Glide
            .with(context)
            .load("${Config.MOVIE_DB_IMAGE_BASE}${item.backdrop_path}")
            .centerCrop()
            .placeholder(R.drawable.placeholder)
            .into(holder.imageView);

        holder.contentView.text = item.title

        holder.itemView.setOnClickListener {

            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra(MOVIE_DETAIL_ACTIVITY_BUNDLE_KEY,item.id)
            (context as Activity).startActivity(intent)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentSearchResultsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val imageView: ImageView = binding.resultImage
        val contentView: TextView = binding.resultTitle

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}