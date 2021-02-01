package mx.com.movieschallenge.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import mx.com.movieschallenge.data.model.MovieData

@JsonClass(generateAdapter = true)
data class PageMovie(
        @Json(name = "page")
        val page: Int? = null,
        @Json(name = "total_pages")
        val totalPage: Int? = null,
        @Json(name = "results")
        val results: MutableList<MovieData> = mutableListOf()
)