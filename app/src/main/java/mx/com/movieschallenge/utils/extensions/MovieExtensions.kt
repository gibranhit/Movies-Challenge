package mx.com.movieschallenge.utils.extensions

import mx.com.movieschallenge.R
import mx.com.movieschallenge.utils.Constants.URL_IMAGE


val String.posterFullUrl: String
    get() = "${URL_IMAGE}$this"

val Boolean.imageFavoriteResource: Int
    get() = if (this) R.drawable.ic_favorite_white else R.drawable.ic_favorite_border