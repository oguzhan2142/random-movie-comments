package com.oguzhan.moviecommentsapp.view

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
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


class MyItemRecyclerViewAdapter(
    private val context:Context,
    private val values: MutableList<Result>
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

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