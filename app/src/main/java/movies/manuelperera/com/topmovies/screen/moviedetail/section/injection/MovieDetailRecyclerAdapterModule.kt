package movies.manuelperera.com.topmovies.screen.moviedetail.section.injection

import android.content.Context
import dagger.Module
import dagger.Provides
import movies.manuelperera.com.topmovies.screen.moviedetail.section.MovieDetailRecyclerAdapterPresenter
import movies.manuelperera.com.topmovies.usecase.movie.GetConfigUseCase
import movies.manuelperera.com.topmovies.usecase.movie.GetMovieDetailUseCase
import movies.manuelperera.com.topmovies.usecase.movie.GetSimilarMoviesUseCase
import movies.manuelperera.com.topmovies.usecase.movie.SetSimilarMoviesPaginationUseCase

@Module
class MovieDetailRecyclerAdapterModule {

    @Provides
    @MovieDetailRecyclerAdapterScope
    fun movieDetailRecyclerAdapterPresenter(getSimilarMoviesUseCase: GetSimilarMoviesUseCase,
                                            setSimilarMoviesPaginationUseCase: SetSimilarMoviesPaginationUseCase,
                                            getConfigUseCase: GetConfigUseCase,
                                            getMovieDetailUseCase: GetMovieDetailUseCase,
                                            context: Context): MovieDetailRecyclerAdapterPresenter =
            MovieDetailRecyclerAdapterPresenter(getSimilarMoviesUseCase, setSimilarMoviesPaginationUseCase, getConfigUseCase, getMovieDetailUseCase, context)

}