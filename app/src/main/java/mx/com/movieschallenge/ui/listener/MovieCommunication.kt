package mx.com.movieschallenge.ui.listener

import android.view.View
import mx.com.movieschallenge.domain.model.Movie

interface MovieCommunication {
    fun selectMovie(poster: View, title: View, ranking: View, movie: Movie)
}