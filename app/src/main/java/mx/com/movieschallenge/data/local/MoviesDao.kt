package mx.com.movieschallenge.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mx.com.movieschallenge.data.model.MovieData

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(movie: MovieData)

    @Query("SELECT * FROM movies WHERE page =(:page)")
    suspend fun getPopularLocalMovies(page: Int): MutableList<MovieData>

    @Query("SELECT*FROM movies WHERE isFavorite = 1")
    suspend fun getFavoritesMovies(): MutableList<MovieData>

    @Query("UPDATE movies SET isFavorite = (:isFavorite) WHERE id = (:id)")
    suspend fun updateFavorite(id: Long, isFavorite: Boolean)
}