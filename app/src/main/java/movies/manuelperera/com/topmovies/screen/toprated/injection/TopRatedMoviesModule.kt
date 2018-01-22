package movies.manuelperera.com.topmovies.screen.toprated.injection

import android.content.Context
import dagger.Module
import dagger.Provides
import movies.manuelperera.com.topmovies.screen.toprated.TopRatedMoviesPresenter
import movies.manuelperera.com.topmovies.usecase.movie.GetConfigUseCase

@Module
class TopRatedMoviesModule {

    @Provides
    @TopRatedMoviesScope
    fun topRatedMoviesPresenter(getConfigUseCase: GetConfigUseCase,
                                context: Context): TopRatedMoviesPresenter =
            TopRatedMoviesPresenter(getConfigUseCase, context)

}