package mx.com.movieschallenge.ui.detail

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import eightbitlab.com.blurview.RenderScriptBlur
import kotlinx.android.synthetic.main.activity_detail_movie.*
import mx.com.movieschallenge.R
import mx.com.movieschallenge.domain.model.Movie
import mx.com.movieschallenge.utils.Constants.MOVIE
import mx.com.movieschallenge.utils.Constants.RANKING
import mx.com.movieschallenge.utils.Constants.TITLE
import mx.com.movieschallenge.utils.extensions.loadImageUrl
import mx.com.movieschallenge.utils.extensions.posterFullUrl
import mx.com.movieschallenge.utils.extensions.startEnterTransitionAfterLoadingImage


class DetailMovieActivity : AppCompatActivity() {

    private var movie: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
        intent.extras?.let {
            movie = it.getParcelable(MOVIE)
        }
        setUpMovieData()
        addBlur()
    }

    private fun setUpMovieData() {
        movie?.let { item ->

            loadImageUrl(backgroundPoster, item.posterPath.posterFullUrl)

            ivMoviePoster.apply {
                transitionName = item.posterPath
                startEnterTransitionAfterLoadingImage(item.posterPath.posterFullUrl, this)
            }
            tvMovieTitle.apply {
                transitionName = TITLE
                text = item.title
            }

            tvTotalPointsItem.apply {
                transitionName = RANKING
                text = getString(R.string.text_placeholder_votes,
                    item.voteAverage.toString(), item.voteCount.toString())
            }

            tvMovieOverview.text = item.overview
        }
    }

    private fun addBlur() {
        val decorView = window.decorView
        val rootView = decorView.findViewById<View>(android.R.id.content) as ViewGroup
        val windowBackground = decorView.background

        blurView.setupWith(rootView)
                .setFrameClearDrawable(windowBackground)
                .setBlurAlgorithm(RenderScriptBlur(this))
                .setBlurRadius(20f)
                .setBlurAutoUpdate(true)
                .setHasFixedTransformationMatrix(true)
    }
}