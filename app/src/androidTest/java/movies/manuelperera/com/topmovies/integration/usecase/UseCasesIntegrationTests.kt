package movies.manuelperera.com.topmovies.integration.usecase

import movies.manuelperera.com.topmovies.TopMoviesApp
import movies.manuelperera.com.topmovies.injections.AppComponent
import movies.manuelperera.com.topmovies.injections.DaggerAppComponent
import movies.manuelperera.com.topmovies.injections.module.AppModule

abstract class UseCasesIntegrationTests {

    protected var daggerAppComponent: AppComponent = DaggerAppComponent.builder().appModule(AppModule(TopMoviesApp())).build()

}