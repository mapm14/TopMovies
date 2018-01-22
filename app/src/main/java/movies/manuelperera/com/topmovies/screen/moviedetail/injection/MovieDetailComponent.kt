package movies.manuelperera.com.topmovies.screen.moviedetail.injection

import dagger.Component
import movies.manuelperera.com.topmovies.injections.AppComponent
import movies.manuelperera.com.topmovies.screen.moviedetail.MovieDetailActivity

@MovieDetailScope
@Component(dependencies = [(AppComponent::class)], modules = [(MovieDetailModule::class)])
interface MovieDetailComponent {

    fun inject(movieDetailActivity: MovieDetailActivity)

}