package com.movieapppoc.movielist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movieapppoc.movielist.domain.repository.MovieListRepository
import com.movieapppoc.movielist.util.Category
import com.movieapppoc.movielist.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieListRepository: MovieListRepository
) : ViewModel() {
    private var _movieListState = MutableStateFlow(MovieListState())

    val movieListState = _movieListState.asStateFlow()

    init {
        println("xxx init block called")
        getPopularMovieList(false)
        getUpcomingMovieList(false)
    }

    fun onEvent(events: MovieListUIEvents) {
        when (events) {
            MovieListUIEvents.Navigate -> {
                _movieListState.update {
                    it.copy(isCurrentPopularScreen = !movieListState.value.isCurrentPopularScreen)

                }
            }

            is MovieListUIEvents.Paginate -> {
                if (events.category == Category.POPULAR) {
                    getPopularMovieList(true)
                } else if (events.category == Category.UPCOMING) {
                    getUpcomingMovieList(true)
                }
            }
        }
    }

    private fun getUpcomingMovieList(forceFetchFromRemote: Boolean) {
        viewModelScope.launch {
            _movieListState.update {
                it.copy(isLoading = true)
            }
            movieListRepository.getMovieList(
                forceFetchRemote = forceFetchFromRemote,
                Category.POPULAR,
                movieListState.value.popularMovieListPage
            ).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _movieListState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is Resource.Success -> {
                        result.data?.let { popularList ->
                            _movieListState.update {
                                it.copy(
                                    isLoading = false,
                                    popularMovieList = movieListState.value.popularMovieList + popularList.shuffled(),
                                    popularMovieListPage = movieListState.value.popularMovieListPage + 1
                                )
                            }
                        }
                    }

                    is Resource.Loading -> {
                        _movieListState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                }

            }
        }

    }

    private fun getPopularMovieList(forceFetchFromRemote: Boolean) {
        viewModelScope.launch {
            _movieListState.update {
                it.copy(isLoading = true)
            }
            movieListRepository.getMovieList(
                forceFetchRemote = forceFetchFromRemote,
                Category.UPCOMING,
                movieListState.value.upcomingMovieListPage
            ).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _movieListState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is Resource.Success -> {
                        println("xxx getPopularMovieList block called ${result.data}")
                        result.data?.let { upcomingList ->
                            _movieListState.update {
                                it.copy(
                                    isLoading = false,
                                    upcomingMovieList = movieListState.value.upcomingMovieList + upcomingList.shuffled(),
                                    upcomingMovieListPage = movieListState.value.upcomingMovieListPage + 1
                                )
                            }
                        }
                    }

                    is Resource.Loading -> {
                        _movieListState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                }

            }
        }


    }
}