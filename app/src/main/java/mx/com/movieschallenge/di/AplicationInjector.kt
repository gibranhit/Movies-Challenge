package mx.com.movieschallenge.di

import androidx.room.Room
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import mx.com.movieschallenge.data.local.MoviesDao
import mx.com.movieschallenge.data.local.MoviesDataBase
import mx.com.movieschallenge.data.remote.HttpInterceptor.createOkHttpInterceptor
import mx.com.movieschallenge.data.remote.MovieEndpoints
import mx.com.movieschallenge.data.repository.FavoriteMoviesRepository
import mx.com.movieschallenge.data.repository.FavoriteMoviesRepositoryImpl
import mx.com.movieschallenge.data.repository.PopularMoviesRepository
import mx.com.movieschallenge.data.repository.PopularMoviesRepositoryImpl
import mx.com.movieschallenge.domain.usecase.GetFavoritesMoviesUseCase
import mx.com.movieschallenge.domain.usecase.GetFavoritesMoviesUseCaseImpl
import mx.com.movieschallenge.domain.usecase.GetPopularMoviesUseCase
import mx.com.movieschallenge.domain.usecase.GetPopularMoviesUseCaseImpl
import mx.com.movieschallenge.ui.favorite.FavoritesMoviesViewModel
import mx.com.movieschallenge.ui.popular.PopularMoviesViewModel
import mx.com.movieschallenge.utils.Constants.DATA_BASE_NAME_MOVIES
import mx.com.movieschallenge.utils.Constants.URL_BASE
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

val ApplicationModule = module {

    factory<PopularMoviesRepository> {
        PopularMoviesRepositoryImpl(get() as MovieEndpoints, get() as MoviesDao)
    }

    factory<FavoriteMoviesRepository> {
        FavoriteMoviesRepositoryImpl(get() as MoviesDao)
    }

    factory<GetPopularMoviesUseCase> {
        GetPopularMoviesUseCaseImpl(get() as PopularMoviesRepository,
            get() as FavoriteMoviesRepository)
    }
    viewModel {
        PopularMoviesViewModel(get() as GetPopularMoviesUseCase)
    }

    factory<GetFavoritesMoviesUseCase> {
        GetFavoritesMoviesUseCaseImpl(get() as FavoriteMoviesRepository)
    }

    viewModel {
        FavoritesMoviesViewModel(get() as GetFavoritesMoviesUseCase)
    }
}


val NetworkModule = module {
    single {
        Room.databaseBuilder(androidContext(),
            MoviesDataBase::class.java, DATA_BASE_NAME_MOVIES).build()
    }

    single { get<MoviesDataBase>().moviesDao() }

    single {
        OkHttpClient.Builder()
            .addInterceptor(createOkHttpInterceptor())
            .addNetworkInterceptor(StethoInterceptor())
            .build()
    }

    single {
        Retrofit.Builder()
            .client(get<OkHttpClient>())
            .baseUrl(URL_BASE)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutinesResponseCallAdapterFactory())
            .build()
    }

    single { get<Retrofit>().create(MovieEndpoints::class.java) }
}