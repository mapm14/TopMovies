package movies.manuelperera.com.topmovies.domain.service

import io.reactivex.Completable
import io.reactivex.Observable
import manuelperera.com.base.client.transaction.Transaction
import manuelperera.com.base.client.transaction.TransactionStatus
import movies.manuelperera.com.topmovies.domain.model.MovieModel
import movies.manuelperera.com.topmovies.domain.objects.domain.ConfigAppDomain
import movies.manuelperera.com.topmovies.domain.objects.domain.TopRatedMoviesAppDomain
import movies.manuelperera.com.topmovies.domain.objects.ui.MovieUI

open class MovieService(private val movieModel: MovieModel) {

    private var movieUI: MovieUI? = null
    private var topRatedMoviesPagination: Int = 1

    open fun setMovie(movie: MovieUI): Completable =
            Completable.create {
                movieUI = movie
                it.onComplete()
            }

    open fun getMovie(): Observable<Transaction<MovieUI>> =
            Observable.just(Transaction(movieUI, if (movieUI != null) TransactionStatus.SUCCESS else TransactionStatus.EXCEPTION))

    open fun getTopRatedMovies(): Observable<Transaction<TopRatedMoviesAppDomain>> =
            movieModel.getTopRatedMovies(topRatedMoviesPagination)

    fun setTopRatedMoviesPagination(page: Int): Observable<Any> =
            Observable.create { observer ->
                topRatedMoviesPagination = page
                observer.onNext(topRatedMoviesPagination)
                observer.onComplete()
            }

}