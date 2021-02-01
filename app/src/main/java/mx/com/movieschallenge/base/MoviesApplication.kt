package mx.com.movieschallenge.base

import android.app.Application
import mx.com.movieschallenge.di.ApplicationModule
import mx.com.movieschallenge.di.NetworkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MoviesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        //Start koin modules
        startKoin {
            androidContext(this@MoviesApplication)
            modules(listOf(NetworkModule, ApplicationModule))
        }
    }
}