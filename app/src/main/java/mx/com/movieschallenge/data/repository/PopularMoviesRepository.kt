@file:Suppress("UNCHECKED_CAST")

package mx.com.movieschallenge.data.repository

import mx.com.movieschallenge.data.local.MoviesDao
import mx.com.movieschallenge.data.mapper.toDomainModel
import mx.com.movieschallenge.data.model.MovieData
import mx.com.movieschallenge.data.model.PageMovie
import mx.com.movieschallenge.data.remote.MovieEndpoints
import mx.com.movieschallenge.domain.model.Movie


interface PopularMoviesRepository : BaseRepository {
    suspend fun getPopularMoviesDB(page: Int): List<Movie>
    suspend fun getPopularMoviesNetwork(page: Int): List<Movie>
}

class PopularMoviesRepositoryImpl(private val movieEndpoints: MovieEndpoints,
                                  private val moviesDao: MoviesDao) : PopularMoviesRepository {
    private suspend fun saveOnDataBase(pageMovie: PageMovie) {
        pageMovie.results.toList().also { listMovies ->
            listMovies.forEach {
                it.page = pageMovie.page
                moviesDao.insert(it)
            }
        }
    }

    override suspend fun getPopularMoviesDB(page: Int): List<Movie>
            = sortMovies(moviesDao.getPopularLocalMovies(page))

    override suspend fun getPopularMoviesNetwork(page: Int): List<Movie> {
        val pageMovie = movieEndpoints.getPopularMovies(page)
        saveOnDataBase(pageMovie)
        return sortMovies(pageMovie.results)
    }
}

