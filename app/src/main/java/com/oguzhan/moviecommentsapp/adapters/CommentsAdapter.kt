package com.oguzhan.moviecommentsapp.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.mancj.materialsearchbar.MaterialSearchBar
import com.oguzhan.moviecommentsapp.R
import com.oguzhan.moviecommentsapp.model.Comment


class CommentsAdapter(private val context: Context, private val comments: MutableList<Comment>) :
    RecyclerView.Adapter<CommentsAdapter.PlaceHolder>() {


    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsAdapter.PlaceHolder {

        val inflater = LayoutInflater.from(context)
        val inflatedView = inflater.inflate(R.layout.list_item_comment, parent, false)

        return PlaceHolder(inflatedView)
    }


    override fun onBindViewHolder(holder: CommentsAdapter.PlaceHolder, position: Int) {

        val comment = comments[position]
        holder.nameTextView.text = comment.movieName
        holder.contentTextView.text = comment.content
        holder.sourceTextView.text = comment.source



        holder.itemView.setOnClickListener {
            val bar = (context as Activity).findViewById<MaterialSearchBar>(R.id.searchBar)
            bar.text =
                comment.movieName
            bar.openSearch()

        }
        holder.moreBotton.setOnClickListener {
            Log.d(TAG, "onBindViewHolder: basildi")
            val popup = PopupMenu(context, holder.moreBotton)

            popup.menuInflater.inflate(R.menu.more_menu, popup.menu)

            popup.setOnMenuItemClickListener {

                return@setOnMenuItemClickListener when (it.itemId) {
                    R.id.copy_url -> {
                        val service =
                            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        service.setPrimaryClip(ClipData.newPlainText("film url", comment.movieUrl))

                        Toast.makeText(
                            context,
                            "${comment.movieUrl}\npanoya kopyalandı",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.d(TAG, "onOptionsItemSelected: deneme 1 basildi")
                        true
                    }
                    R.id.deneme2 -> {
                        Log.d(TAG, "onOptionsItemSelected: deneme 2 basildi")
                        true
                    }
                    else -> false
                }

            }
            popup.show()

        }
    }


    inner class PlaceHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val nameTextView = itemView.findViewById<TextView>(R.id.movie_name)
        val contentTextView = itemView.findViewById<TextView>(R.id.content)
        val sourceTextView = itemView.findViewById<TextView>(R.id.source)

        val moreBotton = itemView.findViewById<ImageButton>(R.id.more_button)


    }
}