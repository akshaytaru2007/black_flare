package com.movieapppoc.movielist.data.local.movie

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert

@Dao
interface MovieDao {

    @Upsert
    suspend fun upsertMovieList(movieList: List<MovieEntity>)

    @Query("SELECT * FROM MOVIE_TABLE WHERE id=:id")
    suspend fun getMovieById(id: Int): MovieEntity?

    @Query("SELECT * FROM MOVIE_TABLE WHERE category=:category")
    suspend fun getMovieByCategory(category: String): List<MovieEntity>?
}
