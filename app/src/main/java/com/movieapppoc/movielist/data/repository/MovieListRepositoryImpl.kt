package com.movieapppoc.movielist.data.repository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.movieapppoc.movielist.data.local.movie.MovieDatabase
import com.movieapppoc.movielist.data.mappers.toMovie
import com.movieapppoc.movielist.data.mappers.toMovieEntity
import com.movieapppoc.movielist.data.remote.MovieApi
import com.movieapppoc.movielist.domain.model.Movie
import com.movieapppoc.movielist.domain.repository.MovieListRepository
import com.movieapppoc.movielist.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class MovieListRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDatabase: MovieDatabase
) : MovieListRepository {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getMovieList(
        forceFetchRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading(true))
            val localMovieList = movieDatabase.movieDao.getMovieByCategory(category)
            val shouldLoadLocalMovies = localMovieList?.isEmpty() == true && !forceFetchRemote
            if (shouldLoadLocalMovies) {
                emit(Resource.Success(
                    data = localMovieList!!.map { movieEntity ->
                        movieEntity.toMovie(category)
                    }
                ))
                emit(Resource.Loading(false))
                return@flow
            }
            val movieListFromAPI = try {
                movieApi.getMoviesList(category, page)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error Loading movies"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error Loading movies"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error Loading movies"))
                return@flow
            }
            val movieEntities = movieListFromAPI.results.let {
                it.map { movieDto -> movieDto.toMovieEntity(category) }
            }
            movieDatabase.movieDao.upsertMovieList(movieEntities)

            emit(Resource.Success(
                movieEntities.map { it.toMovie(category) }
            ))
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getMovie(id: Int): Flow<Resource<Movie>> {
        return flow {
            emit(Resource.Loading(true))
            val movieEntity = movieDatabase.movieDao.getMovieById(id)
            if(movieEntity != null) {
                emit(
                    Resource.Success(movieEntity.toMovie(movieEntity.category))
                )
            }
            emit(Resource.Error("No such movie"))

            emit(Resource.Loading(false))
            return@flow
        }
    }
}