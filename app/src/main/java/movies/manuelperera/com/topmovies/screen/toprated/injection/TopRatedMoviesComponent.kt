package movies.manuelperera.com.topmovies.screen.toprated.injection

import dagger.Component
import movies.manuelperera.com.topmovies.injections.AppComponent
import movies.manuelperera.com.topmovies.screen.toprated.TopRatedMoviesActivity

@TopRatedMoviesScope
@Component(dependencies = [(AppComponent::class)], modules = [(TopRatedMoviesModule::class)])
interface TopRatedMoviesComponent {

    fun inject(topRatedMoviesActivity: TopRatedMoviesActivity)

}