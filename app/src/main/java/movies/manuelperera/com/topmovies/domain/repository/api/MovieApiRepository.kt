package movies.manuelperera.com.topmovies.domain.repository.api

import io.reactivex.Observable
import manuelperera.com.base.client.transaction.Transaction
import manuelperera.com.base.client.transaction.TransactionRequestFactory
import movies.manuelperera.com.topmovies.client.apiclient.MovieApiClient
import movies.manuelperera.com.topmovies.domain.objects.api.TopRatedMoviesApiResponse
import movies.manuelperera.com.topmovies.domain.objects.domain.TopRatedMoviesAppDomain

open class MovieApiRepository(private val movieApiClient: MovieApiClient,
                              private val topRatedMoviesTransactionRequestFactory: TransactionRequestFactory<TopRatedMoviesApiResponse>) {

    open fun getTopRatedMovies(page: Int): Observable<Transaction<TopRatedMoviesAppDomain>> =
            topRatedMoviesTransactionRequestFactory.createTransactionRequest().modifyObservable(movieApiClient.getTopRatedMovies(page)).map { transaction ->
                Transaction(transaction.data?.toAppDomain(), transaction.status)
            }

}