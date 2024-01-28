package com.movieapppoc.movielist.domain.repository

import com.movieapppoc.movielist.domain.model.Movie
import com.movieapppoc.movielist.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieListRepository {

    suspend fun getMovieList(
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>>

    suspend fun getMovie(id: Int): Flow<Resource<Movie>>
}