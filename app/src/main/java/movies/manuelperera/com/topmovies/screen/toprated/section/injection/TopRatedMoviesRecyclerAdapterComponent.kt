package movies.manuelperera.com.topmovies.screen.toprated.section.injection

import dagger.Component
import movies.manuelperera.com.topmovies.injections.AppComponent
import movies.manuelperera.com.topmovies.screen.toprated.section.TopRatedMoviesRecyclerAdapter

@TopRatedMoviesRecyclerAdapterScope
@Component(dependencies = [(AppComponent::class)], modules = [(TopRatedMoviesRecyclerAdapterModule::class)])
interface TopRatedMoviesRecyclerAdapterComponent {

    fun inject(topRatedMoviesRecyclerAdapter: TopRatedMoviesRecyclerAdapter)

}