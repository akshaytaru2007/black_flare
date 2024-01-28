package com.movieapppoc.movielist.data.repository

import android.os.Build
import androidx.annotation.RequiresExtension
import com.movieapppoc.movielist.data.local.MovieLocalDataSource
import com.movieapppoc.movielist.data.mappers.toMovie
import com.movieapppoc.movielist.data.mappers.toMovieEntity
import com.movieapppoc.movielist.data.remote.MovieRemoteDataSource
import com.movieapppoc.movielist.domain.model.Movie
import com.movieapppoc.movielist.domain.repository.MovieListRepository
import com.movieapppoc.movielist.util.Resource
import com.movieapppoc.movielist.util.exceptions.ClientErrorException
import com.movieapppoc.movielist.util.exceptions.ServerErrorException
import com.movieapppoc.movielist.util.network.ConnectivityProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieListRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieLocalDataSource: MovieLocalDataSource,
    private val connectivityProvider: ConnectivityProvider
) : MovieListRepository {

    override suspend fun getMovieList(
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading(true))
            val localMovieList = movieLocalDataSource.getMovieByCategory(category)
            val shouldLoadLocalMovies = !connectivityProvider.isConnected()
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
                movieRemoteDataSource.getMoviesList(category, page)
            } catch (e: Throwable) {
                e.printStackTrace()
                if (e is ServerErrorException || e is ClientErrorException)
                    emit(Resource.Error(message = "Network Error"))
                else
                    emit(Resource.Error(message = "Error Loading movies"))
                return@flow
            }
            val movieEntities = movieListFromAPI.results.let {
                it.map { movieDto -> movieDto.toMovieEntity(category) }
            }
            movieLocalDataSource.upsertMovieList(movieEntities)

            emit(Resource.Success(
                movieEntities.map { it.toMovie(category) }
            ))
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getMovie(id: Int): Flow<Resource<Movie>> {
        return flow {
            emit(Resource.Loading(true))
            val movieEntity = movieLocalDataSource.getMovieById(id)
            if (movieEntity != null) {
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