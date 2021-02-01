package mx.com.movieschallenge.domain.usecase

import mx.com.movieschallenge.data.repository.FavoriteMoviesRepository
import mx.com.movieschallenge.domain.model.Movie

interface GetFavoritesMoviesUseCase : BaseUseCase {
   suspend fun getFavorites(): List<Movie>
}

class GetFavoritesMoviesUseCaseImpl(private val favoriteMoviesRepository: FavoriteMoviesRepository) :
    GetFavoritesMoviesUseCase {

    override suspend fun getFavorites(): List<Movie> {
        return favoriteMoviesRepository.getFavorites()
    }

    override suspend fun updateFavoriteState(id: Long, isFavorite: Boolean) {
        favoriteMoviesRepository.updateFavoriteState(id, isFavorite)
    }
}