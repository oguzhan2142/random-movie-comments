package com.oguzhan.moviecommentsapp.view

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.oguzhan.moviecommentsapp.R
import com.oguzhan.moviecommentsapp.model.Result
import com.oguzhan.moviecommentsapp.viewmodel.MoviedbViewModel
import com.oguzhan.moviecommentsapp.viewmodel.SearchResultsViewModel

/**
 * A fragment representing a list of Items.
 */
class SearchResultsFragment : Fragment() {

    private var columnCount = 1
    private lateinit var moviedbViewModel: MoviedbViewModel
    private lateinit var searchResultsViewModel: SearchResultsViewModel

    private lateinit var  resultsAdapter :MyItemRecyclerViewAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search_results_list, container, false)
        moviedbViewModel = ViewModelProvider(requireActivity()).get(MoviedbViewModel::class.java)
        searchResultsViewModel = ViewModelProvider(requireActivity()).get(SearchResultsViewModel::class.java)

        resultsAdapter = MyItemRecyclerViewAdapter(searchResultsViewModel.searchResults)
        moviedbViewModel.searchResults.observe(viewLifecycleOwner, {


            Log.d(TAG, "onCreateView: yeni liste uzunlugu ${it.size}")
            searchResultsViewModel.updateResults(it)
            resultsAdapter.notifyDataSetChanged()

        })

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = resultsAdapter
            }
        }
        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            SearchResultsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}