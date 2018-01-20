package movies.manuelperera.com.topmovies.injections.module

import android.content.Context
import dagger.Module
import dagger.Provides
import movies.manuelperera.com.topmovies.TopMoviesApp
import javax.inject.Singleton

@Module
class AppModule(private val app: TopMoviesApp) {

    @Provides
    @Singleton
    fun context(): Context = app.applicationContext

}