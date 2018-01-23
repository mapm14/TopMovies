package movies.manuelperera.com.topmovies.domain.model

import io.reactivex.Observable
import manuelperera.com.base.client.transaction.Transaction
import movies.manuelperera.com.topmovies.domain.objects.domain.MovieDetailAppDomain
import movies.manuelperera.com.topmovies.domain.objects.domain.MoviesListAppDomain
import movies.manuelperera.com.topmovies.domain.repository.api.MovieApiRepository

open class MovieModel(private val movieApiRepository: MovieApiRepository) {

    open fun getTopRatedMovies(page: Int): Observable<Transaction<MoviesListAppDomain>> =
            movieApiRepository.getTopRatedMovies(page)

    open fun getSimilarMovies(movieId: Int, page: Int): Observable<Transaction<MoviesListAppDomain>> =
            movieApiRepository.getSimilarMovies(movieId, page)

    open fun getMovieDetail(movieId: Int): Observable<Transaction<MovieDetailAppDomain>> =
            movieApiRepository.getMovieDetail(movieId)

}