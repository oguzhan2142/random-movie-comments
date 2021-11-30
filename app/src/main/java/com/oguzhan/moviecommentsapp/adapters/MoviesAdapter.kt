package com.oguzhan.moviecommentsapp.adapters



import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.makeramen.roundedimageview.RoundedImageView
import com.oguzhan.moviecommentsapp.R
import com.oguzhan.moviecommentsapp.model.Item

class MoviesAdapter(private val imdbMovies: List<Item>, private val context: Context) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)

        val view = inflater.inflate(R.layout.movie_list_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = imdbMovies[position]


        holder.imdbRate.text = movie.imDbRating
        holder.movieName.text = movie.title
        holder.year.text = "| ${movie.year}"
        Glide.with(context).load(movie.image).into(holder.poster)

    }

    override fun getItemCount(): Int {
        return imdbMovies.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val poster = itemView.findViewById<RoundedImageView>(R.id.poster)
        val imdbRate = itemView.findViewById<TextView>(R.id.imdb_rate)
        val movieName = itemView.findViewById<TextView>(R.id.name)
        val year = itemView.findViewById<TextView>(R.id.year)

    }
}