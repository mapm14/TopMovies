package movies.manuelperera.com.topmovies.injections.module.usecase

import dagger.Module
import dagger.Provides
import movies.manuelperera.com.topmovies.domain.service.ConfigService
import movies.manuelperera.com.topmovies.domain.service.MovieService
import movies.manuelperera.com.topmovies.usecase.movie.*

@Module
class MoviesUseCaseModule {

    @Provides
    fun getTopRatedMoviesUseCase(movieService: MovieService, configService: ConfigService): GetTopRatedMoviesUseCase =
            GetTopRatedMoviesUseCase(movieService, configService)

    @Provides
    fun setMovieIdUseCase(movieService: MovieService): SetMovieSelectedUseCase =
            SetMovieSelectedUseCase(movieService)

    @Provides
    fun setTopRatedMoviesPaginationUseCase(movieService: MovieService): SetTopRatedMoviesPaginationUseCase =
            SetTopRatedMoviesPaginationUseCase(movieService)

    @Provides
    fun getMovieSelectedUseCase(movieService: MovieService): GetMovieSelectedUseCase =
            GetMovieSelectedUseCase(movieService)

    @Provides
    fun getSimilarMoviesUseCase(movieService: MovieService, configService: ConfigService): GetSimilarMoviesUseCase =
            GetSimilarMoviesUseCase(movieService, configService)

    @Provides
    fun setSimilarMoviesPaginationUseCase(movieService: MovieService): SetSimilarMoviesPaginationUseCase =
            SetSimilarMoviesPaginationUseCase(movieService)

    @Provides
    fun getMovieDetailUseCase(movieService: MovieService): GetMovieDetailUseCase =
            GetMovieDetailUseCase(movieService)

}