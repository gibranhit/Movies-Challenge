package mx.com.movieschallenge.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_popular_movie.view.*
import mx.com.movieschallenge.R
import mx.com.movieschallenge.databinding.ItemPopularMovieBinding
import mx.com.movieschallenge.domain.model.Movie
import mx.com.movieschallenge.utils.Constants.RANKING
import mx.com.movieschallenge.utils.Constants.TITLE
import mx.com.movieschallenge.utils.extensions.imageFavoriteResource
import mx.com.movieschallenge.utils.extensions.posterFullUrl

class MoviesAdapter(private val movies: MutableList<Movie>) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private var update:((Movie) -> Unit)? = null

    private var listener:((View, View, View, Movie) -> Unit)? = null

    fun setItemClickListener(listener:((View, View, View, Movie) -> Unit)) {
        this.listener = listener
    }

    fun setUpdateListener(listener: (Movie) -> Unit) {
        this.update = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemPopularMovieBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int = movies.size

    fun addNewMovies(newMovies: List<Movie>) {
        movies.addAll(newMovies)
        notifyDataSetChanged()
    }

    fun updateMovies(newMovies: List<Movie>) {
        movies.apply {
            clear()
            addAll(newMovies)
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.bindData(movie)
        holder.binding.movie = movie
        holder.binding.executePendingBindings()
    }

    inner class ViewHolder(val binding: ItemPopularMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(movie: Movie) {
            with(binding){
                with(ivMoviePoster) {
                    transitionName = movie.posterPath
                    setOnClickListener { listener?.invoke(binding.ivMoviePoster, binding.tvMovieTitle, binding.tvTotalPointsItem, movie) }

                    with(movie) {
                        Glide.with(context).asDrawable().load(posterPath.posterFullUrl)
                            .placeholder(R.drawable.ic_movie_favorite)
                            .into(ivMoviePoster)
                        //binding.ivMovieFavoriteState.setImageResource(isFavorite.imageFavoriteResource)
                    }
                }
                with(tvMovieTitle) {
                    transitionName = TITLE
                }

                with(tvTotalPointsItem) {
                    transitionName = RANKING
                }

                ivMovieFavoriteState.apply{
                    setOnClickListener { update?.invoke(movie) }
                    Glide.with(context).asDrawable().load(movie.isFavorite.imageFavoriteResource)
                        .into(this)
                }
            }
        }
    }
}