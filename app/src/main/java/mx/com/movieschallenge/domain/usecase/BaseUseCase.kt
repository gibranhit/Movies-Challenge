package mx.com.movieschallenge.domain.usecase

interface BaseUseCase {
    suspend fun updateFavoriteState(id: Long, isFavorite: Boolean)
}