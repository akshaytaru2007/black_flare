package com.pratham.movieapppoc.local

import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.movieapppoc.movielist.data.local.MovieDatabase
import com.movieapppoc.movielist.data.local.MovieEntity
import com.movieapppoc.movielist.data.local.MovieLocalDataSource
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.MockitoAnnotations


class MovieListLocalDataSourceTest {
    private lateinit var database: MovieDatabase
    private lateinit var localDataSource: MovieLocalDataSource

    @JvmField
    @Rule
    var instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setupDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MovieDatabase::class.java
        ).allowMainThreadQueries().build()

        localDataSource = MovieLocalDataSource(database.movieDao)
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun insertMoviesAndGetAllAsPerCategory() = runBlocking {
        // Given
        val movieTitle = "title"
        val movies = listOf(
            MovieEntity(
                adult = true,
                backdrop_path = "URL",
                genre_ids = "1,2",
                original_language = "en",
                original_title = "Movie title",
                overview = "bbkjbkj bkjbk jbkbj",
                popularity = 0.3,
                poster_path = "url",
                release_date = "09/01/2023",
                title = movieTitle,
                video = true,
                vote_average = 0.9,
                vote_count = 78,
                id = 1,
                category = "Category",
            )
        )
        localDataSource.upsertMovieList(movies)


        // Retrieve all users from the database
        val fetchedList = localDataSource.getMovieByCategory("Category")

        // Verify that the user was inserted and retrieved successfully
        assertEquals(1, fetchedList!!.size)
        assertEquals(movieTitle, fetchedList[0].title)
    }


    @Test
    fun insertMoviesAndGetAllAsPerId() = runBlocking {
        // Given
        val movieTitle = "title"
        val movies = listOf(
            MovieEntity(
                adult = true,
                backdrop_path = "URL",
                genre_ids = "1,2",
                original_language = "en",
                original_title = "Movie title",
                overview = "bbkjbkj bkjbk jbkbj",
                popularity = 0.3,
                poster_path = "url",
                release_date = "09/01/2023",
                title = movieTitle,
                video = true,
                vote_average = 0.9,
                vote_count = 78,
                id = 1,
                category = "Category",
            )
        )
        localDataSource.upsertMovieList(movies)


        // Retrieve movie by id from the database
        val fetchedList = localDataSource.getMovieById(1)

        // Verify that the movie was inserted and retrieved successfully
        assertEquals(movieTitle, fetchedList?.title)
    }

}