package mx.com.movieschallenge.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import mx.com.movieschallenge.ui.model.FavoriteMoviesViewState
import kotlin.coroutines.CoroutineContext

open class BaseViewModel: ViewModel(), CoroutineScope {

    private val job: Job = Job()

    override val coroutineContext: CoroutineContext
    get() = Dispatchers.Main + job

    fun main(work: suspend (()-> Unit)) = CoroutineScope(coroutineContext).launch {
        work()
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    protected fun getFavoriteViewStateWhenUpdate(isFavorite: Boolean): FavoriteMoviesViewState {
        return when {
            isFavorite -> FavoriteMoviesViewState.OnSuccessAddFavorite
            else -> FavoriteMoviesViewState.OnSuccessRemoveFavorite
        }
    }
}