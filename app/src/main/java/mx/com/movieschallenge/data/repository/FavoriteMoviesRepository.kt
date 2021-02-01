package mx.com.movieschallenge.data.repository

import mx.com.movieschallenge.data.local.MoviesDao
import mx.com.movieschallenge.data.mapper.toDomainModel
import mx.com.movieschallenge.domain.model.Movie

interface FavoriteMoviesRepository : BaseRepository {
    suspend fun getFavorites(): List<Movie>
    suspend fun updateFavoriteState(id: Long, isFavorite: Boolean)
}

class FavoriteMoviesRepositoryImpl(private val moviesDao: MoviesDao) : FavoriteMoviesRepository {

    override suspend fun getFavorites(): List<Movie>
       =  moviesDao.getFavoritesMovies().map { result ->
            result.toDomainModel()
        }.sortedByDescending { item -> item.voteAverage }


    override suspend fun updateFavoriteState(id: Long, isFavorite: Boolean) {
        moviesDao.updateFavorite(id, isFavorite)
    }
}