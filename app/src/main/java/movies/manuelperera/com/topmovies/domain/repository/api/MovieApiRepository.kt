package movies.manuelperera.com.topmovies.domain.repository.api

import io.reactivex.Observable
import manuelperera.com.base.client.transaction.Transaction
import manuelperera.com.base.client.transaction.TransactionRequestFactory
import movies.manuelperera.com.topmovies.client.apiclient.MovieApiClient
import movies.manuelperera.com.topmovies.domain.objects.api.MovieDetailApiResponse
import movies.manuelperera.com.topmovies.domain.objects.api.MoviesListApiResponse
import movies.manuelperera.com.topmovies.domain.objects.domain.MovieDetailAppDomain
import movies.manuelperera.com.topmovies.domain.objects.domain.MoviesListAppDomain

open class MovieApiRepository(private val movieApiClient: MovieApiClient,
                              private val moviesListTransactionRequestFactory: TransactionRequestFactory<MoviesListApiResponse>,
                              private val movieDetailTransactionRequestFactory: TransactionRequestFactory<MovieDetailApiResponse>) {

    open fun getTopRatedMovies(page: Int): Observable<Transaction<MoviesListAppDomain>> =
            moviesListTransactionRequestFactory.createTransactionRequest().modifyObservable(movieApiClient.getTopRatedMovies(page)).map { transaction ->
                Transaction(transaction.data?.toAppDomain(), transaction.status, transaction.errorBody)
            }

    open fun getSimilarMovies(movieId: Int, page: Int): Observable<Transaction<MoviesListAppDomain>> =
            moviesListTransactionRequestFactory.createTransactionRequest().modifyObservable(movieApiClient.getSimilarMovies(movieId, page)).map { transaction ->
                Transaction(transaction.data?.toAppDomain(), transaction.status, transaction.errorBody)
            }

    open fun getMovieDetail(movieId: Int): Observable<Transaction<MovieDetailAppDomain>> =
            movieDetailTransactionRequestFactory.createTransactionRequest().modifyObservable(movieApiClient.getMovieDetail(movieId)).map { transaction ->
                Transaction(transaction.data?.toAppDomain(), transaction.status, transaction.errorBody)
            }

}