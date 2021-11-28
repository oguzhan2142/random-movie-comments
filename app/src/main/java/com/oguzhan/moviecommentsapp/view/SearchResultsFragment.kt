package com.oguzhan.moviecommentsapp.view

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oguzhan.moviecommentsapp.R
import com.oguzhan.moviecommentsapp.adapters.SearchResultsRecyclerViewAdapter
import com.oguzhan.moviecommentsapp.utils.ThemeChecker.Companion.isDarkThemeOn
import com.oguzhan.moviecommentsapp.viewmodel.MoviedbViewModel
import com.oguzhan.moviecommentsapp.viewmodel.SearchResultsViewModel


/**
 * A fragment representing a list of Items.
 */
class SearchResultsFragment : Fragment() {

    private lateinit var moviedbViewModel: MoviedbViewModel
    private lateinit var searchResultsViewModel: SearchResultsViewModel

    private lateinit var resultsAdapter: SearchResultsRecyclerViewAdapter

    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search_results_list, container, false)
        moviedbViewModel = ViewModelProvider(requireActivity()).get(MoviedbViewModel::class.java)
        searchResultsViewModel =
            ViewModelProvider(requireActivity()).get(SearchResultsViewModel::class.java)
        recyclerView = view.findViewById(R.id.search_list)

        val itemDecorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)

        val resourceId =
            if (context!!.isDarkThemeOn()) R.drawable.recycler_list_divider_white else R.drawable.recycler_list_divider_black
        itemDecorator.setDrawable(ContextCompat.getDrawable(context!!, resourceId)!!)
        recyclerView.addItemDecoration(itemDecorator)


        resultsAdapter =
            SearchResultsRecyclerViewAdapter(requireActivity(), searchResultsViewModel.searchResults)
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


}