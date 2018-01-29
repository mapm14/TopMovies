package movies.manuelperera.com.topmovies.screen.moviedetail.section.injection

import dagger.Component
import movies.manuelperera.com.topmovies.injections.AppComponent
import movies.manuelperera.com.topmovies.screen.moviedetail.section.MovieDetailRecyclerAdapter

@MovieDetailRecyclerAdapterScope
@Component(dependencies = [(AppComponent::class)], modules = [(MovieDetailRecyclerAdapterModule::class)])
interface MovieDetailRecyclerAdapterComponent {

    fun inject(movieDetailRecyclerAdapter: MovieDetailRecyclerAdapter)

}