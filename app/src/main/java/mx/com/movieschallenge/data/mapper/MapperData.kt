package mx.com.movieschallenge.data.mapper

import mx.com.movieschallenge.data.model.MovieData
import mx.com.movieschallenge.domain.model.Movie

/**
 * An extension to convert data model to domain model
 * @return the Movie's domain model
 */
fun MovieData.toDomainModel() = Movie(
        id = id, title = title ?: "", overview = overview ?: "",
        posterPath = posterPath ?: "", releaseDate = releaseDate ?: "",
        voteAverage = voteAverage ?: 0.0, voteCount = voteCount ?: 0L,
        isFavorite = isFavorite ?: false, page = page ?: 0
)