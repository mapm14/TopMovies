package movies.manuelperera.com.topmovies

import android.app.Application
import android.support.v7.app.AppCompatDelegate
import movies.manuelperera.com.topmovies.injections.AppComponent
import movies.manuelperera.com.topmovies.injections.DaggerAppComponent
import movies.manuelperera.com.topmovies.injections.module.AppModule

class TopMoviesApp : Application() {

    companion object {
        lateinit var mDaggerAppComponent: AppComponent
        fun daggerAppComponent() = mDaggerAppComponent
    }

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        mDaggerAppComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

}