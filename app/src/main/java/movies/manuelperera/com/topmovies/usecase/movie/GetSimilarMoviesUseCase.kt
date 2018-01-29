package movies.manuelperera.com.topmovies.usecase.movie

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import manuelperera.com.base.client.transaction.Transaction
import manuelperera.com.base.usecase.UseCase
import movies.manuelperera.com.topmovies.domain.objects.domain.MovieAppDomain
import movies.manuelperera.com.topmovies.domain.service.ConfigService
import movies.manuelperera.com.topmovies.domain.service.MovieService
import movies.manuelperera.com.topmovies.extensions.addBaseUrlToMovieList

open class GetSimilarMoviesUseCase(val movieService: MovieService,
                                   private val configService: ConfigService) : UseCase<Observable<Transaction<MutableList<MovieAppDomain>>>> {

    override fun bind(): Observable<Transaction<MutableList<MovieAppDomain>>> =
            configService.getConfig().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .flatMap { configTransaction ->
                        movieService.getSimilarMovies().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                                .map { transaction ->
                                    if (transaction.isSuccess())
                                        addBaseUrlToMovieList(configTransaction.data?.images, transaction.data?.movies)

                                    Transaction(transaction.data?.movies, transaction.status)
                                }
                    }

}