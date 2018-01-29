package movies.manuelperera.com.topmovies.domain.service

import io.reactivex.Completable
import io.reactivex.Observable
import manuelperera.com.base.client.transaction.Transaction
import manuelperera.com.base.client.transaction.TransactionStatus
import movies.manuelperera.com.topmovies.domain.model.MovieModel
import movies.manuelperera.com.topmovies.domain.objects.domain.MovieAppDomain
import movies.manuelperera.com.topmovies.domain.objects.domain.MovieDetailAppDomain
import movies.manuelperera.com.topmovies.domain.objects.domain.MoviesListAppDomain

open class MovieService(private val movieModel: MovieModel) {

    private var movieSelected: MovieAppDomain? = null
    private var topRatedMoviesPagination: Int = 1
    private var similarMoviesPagination: Int = 1

    open fun setMovie(movie: MovieAppDomain): Completable =
            Completable.create {
                movieSelected = movie
                it.onComplete()
            }

    open fun getMovie(): Observable<Transaction<MovieAppDomain>> =
            Observable.just(Transaction(movieSelected, if (movieSelected != null) TransactionStatus.SUCCESS else TransactionStatus.EXCEPTION))

    fun setTopRatedMoviesPagination(page: Int): Observable<Any> =
            Observable.create { observer ->
                topRatedMoviesPagination = page
                observer.onNext(topRatedMoviesPagination)
                observer.onComplete()
            }

    open fun getTopRatedMovies(): Observable<Transaction<MoviesListAppDomain>> =
            movieModel.getTopRatedMovies(topRatedMoviesPagination)

    fun setSimilarMoviesPagination(page: Int): Observable<Any> =
            Observable.create { observer ->
                similarMoviesPagination = page
                observer.onNext(similarMoviesPagination)
                observer.onComplete()
            }

    open fun getSimilarMovies(): Observable<Transaction<MoviesListAppDomain>> =
            movieModel.getSimilarMovies(movieSelected?.id ?: 0, similarMoviesPagination)
                    .map { transaction ->
                        if (transaction.isSuccess() && similarMoviesPagination == 1) {
                            transaction.data?.movies?.add(0, movieSelected
                                    ?: MovieAppDomain())
                            Transaction(MoviesListAppDomain(transaction.data?.page
                                    ?: similarMoviesPagination, transaction.data?.movies
                                    ?: mutableListOf()), TransactionStatus.SUCCESS)
                        } else
                            transaction
                    }

    open fun getMovieDetail(movieId: Int): Observable<Transaction<MovieDetailAppDomain>> =
            movieModel.getMovieDetail(movieId)

}