package com.movieapppoc.movielist.data.remote

import com.movieapppoc.movielist.data.remote.response.MovieListDto
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(
    private val movieApi: MovieApi
) {
    suspend fun getMoviesList(
        category: String,
        page: Int
    ): MovieListDto = movieApi.getMoviesList(category, page, MovieApi.API_KEY)
}