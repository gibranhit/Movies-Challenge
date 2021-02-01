package mx.com.movieschallenge.data.remote

import com.skydoves.sandwich.ApiResponse
import mx.com.movieschallenge.data.model.PageMovie
import mx.com.movieschallenge.utils.Constants.API_KEY
import mx.com.movieschallenge.utils.Constants.DEFAULT_LANGUAGE
import mx.com.movieschallenge.utils.Constants.DEFAULT_REGION
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieEndpoints {
    @GET("popular")
    suspend fun getPopularMovies(
            @Query("page") page: Int,
            @Query("api_key") apiKey: String = API_KEY,
            @Query("language") language: String = DEFAULT_LANGUAGE,
            @Query("region") region: String = DEFAULT_REGION
    ): PageMovie
}