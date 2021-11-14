package com.oguzhan.moviecommentsapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.oguzhan.moviecommentsapp.databinding.FragmentSearchResultsBinding
import com.oguzhan.moviecommentsapp.model.Result


class MyItemRecyclerViewAdapter(
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