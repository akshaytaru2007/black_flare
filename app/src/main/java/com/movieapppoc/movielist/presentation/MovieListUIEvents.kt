package com.movieapppoc.movielist.presentation

sealed interface MovieListUIEvents {
    data class Paginate(val category: String) : MovieListUIEvents
    data object Navigate : MovieListUIEvents
}