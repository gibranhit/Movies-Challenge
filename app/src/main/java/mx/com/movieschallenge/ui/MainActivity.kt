package mx.com.movieschallenge.ui

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Pair
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_main.*
import mx.com.movieschallenge.R
import mx.com.movieschallenge.domain.model.Movie
import mx.com.movieschallenge.ui.detail.DetailMovieActivity
import mx.com.movieschallenge.ui.listener.MovieCommunication
import mx.com.movieschallenge.utils.Constants.MOVIE
import mx.com.movieschallenge.utils.Constants.RANKING
import mx.com.movieschallenge.utils.Constants.TITLE

class MainActivity : AppCompatActivity(), MovieCommunication {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpNavigation()
    }

    private fun setUpNavigation() {
        (supportFragmentManager.findFragmentById(R.id.navHostFragment) as? NavHostFragment)?.let { nav ->
            NavigationUI.setupWithNavController(bottomNavigationView, nav.navController)
            bottomNavigationView.setOnNavigationItemSelectedListener {
                if (isValidatedDestination(controller = nav.navController, destination = it.itemId))
                    nav.navController.navigate(it.itemId)
                true
            }
        }
    }

    private fun isValidatedDestination(controller: NavController,destination: Int): Boolean =
            destination != controller.currentDestination?.id

    override fun selectMovie(poster: View, title: View, ranking: View, movie: Movie) {
        val intent = Intent(this, DetailMovieActivity::class.java).apply {
            putExtra(MOVIE, movie)
        }
        val image = movie.posterPath
        val pairPoster : Pair<View, String> = Pair(poster, image)
        val pairTitle : Pair<View, String> = Pair(title, TITLE)
        val pairRanking : Pair<View, String> = Pair(ranking, RANKING)
        val options = ActivityOptions.makeSceneTransitionAnimation(
            this, pairPoster, pairTitle, pairRanking)
        startActivity(intent, options.toBundle())
    }
}