package com.movieapppoc.movielist.data.local

import javax.inject.Inject

class MovieLocalDataSource @Inject constructor(
    private val movieDao: MovieDao
) {

    suspend fun getMovieByCategory(category: String): List<MovieEntity>? =
        movieDao.getMovieByCategory(category)

    suspend fun getMovieById(id: Int): MovieEntity? = movieDao.getMovieById(id)

    suspend fun upsertMovieList(movieList: List<MovieEntity>) {
        movieDao.upsertMovieList(movieList)
    }
}