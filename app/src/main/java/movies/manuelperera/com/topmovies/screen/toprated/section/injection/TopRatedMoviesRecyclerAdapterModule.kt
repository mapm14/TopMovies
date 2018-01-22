package movies.manuelperera.com.topmovies.screen.toprated.section.injection

import android.content.Context
import dagger.Module
import dagger.Provides
import movies.manuelperera.com.topmovies.screen.toprated.section.TopRatedMoviesRecyclerAdapterPresenter
import movies.manuelperera.com.topmovies.usecase.movie.GetConfigUseCase
import movies.manuelperera.com.topmovies.usecase.movie.GetTopRatedMoviesUseCase
import movies.manuelperera.com.topmovies.usecase.movie.SetMovieSelectedUseCase
import movies.manuelperera.com.topmovies.usecase.movie.SetTopRatedMoviesPaginationUseCase

@Module
class TopRatedMoviesRecyclerAdapterModule {

    @Provides
    @TopRatedMoviesRecyclerAdapterScope
    fun topRatedMoviesRecyclerPresenter(getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
                                        setMovieSelectedUseCase: SetMovieSelectedUseCase,
                                        setTopRatedMoviesPaginationUseCase: SetTopRatedMoviesPaginationUseCase,
                                        getConfigUseCase: GetConfigUseCase,
                                        context: Context): TopRatedMoviesRecyclerAdapterPresenter =
            TopRatedMoviesRecyclerAdapterPresenter(getTopRatedMoviesUseCase, setMovieSelectedUseCase, setTopRatedMoviesPaginationUseCase, getConfigUseCase, context)

}