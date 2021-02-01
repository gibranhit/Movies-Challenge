package mx.com.movieschallenge.ui.popular

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import mx.com.movieschallenge.base.BaseViewModel
import mx.com.movieschallenge.domain.model.Movie
import mx.com.movieschallenge.domain.usecase.GetPopularMoviesUseCase
import mx.com.movieschallenge.ui.model.FavoriteMoviesViewState
import mx.com.movieschallenge.ui.model.PopularMoviesViewState
import mx.com.movieschallenge.utils.Constants

class PopularMoviesViewModel(private val useCase: GetPopularMoviesUseCase) : BaseViewModel() {

    //Private fields
    private var _popularViewState = MutableLiveData<PopularMoviesViewState>()
    private var _popularMovies = MutableLiveData<List<Movie>>()
    private var _favoriteViewState = MutableLiveData<FavoriteMoviesViewState>()

    //Public fields
    var currentPage: Int = 1

    val popularMovies: LiveData<List<Movie>>
        get() = _popularMovies

    val popularViewState: LiveData<PopularMoviesViewState>
        get() = _popularViewState

    val favoriteMoviesViewState: LiveData<FavoriteMoviesViewState>
        get() = _favoriteViewState

    init {
        fetchPopularMovies()
    }

    fun fetchPopularMovies() {
        when {
            canLoadMoreMovies(currentPage) -> getPopularMoviesDB()
            else -> _popularViewState.postValue(PopularMoviesViewState.OnMaxPagesReached)
        }
    }

    fun updateFavorite(id: Long, isFavorite: Boolean) {
        main {
            runCatching {
                useCase.updateFavoriteState(id, isFavorite)
            }.onSuccess {
                _favoriteViewState.postValue(getFavoriteViewStateWhenUpdate(isFavorite))
            }.onFailure {
                Log.wtf("Fail", it.localizedMessage)
            }
        }
    }

    private fun getPopularMoviesDB() {
        _popularViewState.postValue(PopularMoviesViewState.OnLoading)
        main {
            runCatching {
               useCase.getPopularMoviesDB(page = currentPage)
            }.onSuccess {
                if (it.isNotEmpty()){
                    currentPage++
                    _popularMovies.postValue(it)
                    _popularViewState.postValue(PopularMoviesViewState.OnSuccessFetch(currentPage))
                } else {
                    getPopularMoviesRequest()
                }
            }.onFailure {
                _popularViewState.postValue(PopularMoviesViewState.OnError)
            }
        }
    }

    private fun getPopularMoviesRequest() {
        main {
            runCatching {
                useCase.getPopularMoviesNetwork(page = currentPage)
            }.onSuccess {
                getPopularMoviesDB()
            }.onFailure {
                _popularViewState.postValue(PopularMoviesViewState.OnError)
            }
        }
    }

    private fun canLoadMoreMovies(currentPage: Int) = currentPage < Constants.MAX_PAGES

}