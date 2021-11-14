package com.oguzhan.moviecommentsapp.viewmodel

import androidx.lifecycle.ViewModel
import com.oguzhan.moviecommentsapp.model.Result

class SearchResultsViewModel :ViewModel() {

    val searchResults = mutableListOf<Result>()





    fun updateResults(newResults:List<Result>){

        searchResults.clear()

        searchResults.addAll(newResults)

    }


}