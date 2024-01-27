package com.movieapppoc.details.presentation

import com.movieapppoc.movielist.domain.model.Movie

data class DetailsState(
    val isLoading: Boolean = false,
    val movie: Movie? = null
)
