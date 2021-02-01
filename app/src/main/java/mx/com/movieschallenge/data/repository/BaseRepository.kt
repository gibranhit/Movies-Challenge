package mx.com.movieschallenge.data.repository

import mx.com.movieschallenge.data.mapper.toDomainModel
import mx.com.movieschallenge.data.model.MovieData
import mx.com.movieschallenge.domain.model.Movie

interface BaseRepository {

    fun sortMovies(movies: List<MovieData>): List<Movie>
        = movies.map { result ->
            result.toDomainModel()
        }.sortedByDescending { item -> item.voteAverage }

}