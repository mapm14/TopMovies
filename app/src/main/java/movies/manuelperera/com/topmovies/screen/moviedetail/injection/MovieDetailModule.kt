package movies.manuelperera.com.topmovies.screen.moviedetail.injection

import dagger.Module
import dagger.Provides
import movies.manuelperera.com.topmovies.screen.moviedetail.MovieDetailPresenter
import movies.manuelperera.com.topmovies.usecase.movie.GetConfigUseCase
import movies.manuelperera.com.topmovies.usecase.movie.GetMovieSelectedUseCase

@Module
class MovieDetailModule {

    @Provides
    @MovieDetailScope
    fun movieDetailPresenter(getMovieSelectedUseCase: GetMovieSelectedUseCase,
                             getConfigUseCase: GetConfigUseCase): MovieDetailPresenter =
            MovieDetailPresenter(getMovieSelectedUseCase, getConfigUseCase)

}