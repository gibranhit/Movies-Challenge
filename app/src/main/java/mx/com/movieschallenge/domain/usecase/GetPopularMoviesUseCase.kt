package mx.com.movieschallenge.domain.usecase

import mx.com.movieschallenge.data.repository.FavoriteMoviesRepository
import mx.com.movieschallenge.data.repository.PopularMoviesRepository
import mx.com.movieschallenge.domain.model.Movie

interface GetPopularMoviesUseCase : BaseUseCase {
    suspend fun getPopularMoviesDB(page: Int): List<Movie>
    suspend fun getPopularMoviesNetwork(page: Int): List<Movie>
}

class GetPopularMoviesUseCaseImpl(private val repositoryPopularMovies: PopularMoviesRepository,
                                  private val repositoryFavoriteMovies: FavoriteMoviesRepository) : GetPopularMoviesUseCase {

    override suspend fun getPopularMoviesDB(page: Int): List<Movie> = repositoryPopularMovies.getPopularMoviesDB(page)

    override suspend fun getPopularMoviesNetwork(page: Int): List<Movie> = repositoryPopularMovies.getPopularMoviesNetwork(page)

    override suspend fun updateFavoriteState(id: Long, isFavorite: Boolean) {
        repositoryFavoriteMovies.updateFavoriteState(id, isFavorite)
    }
}
