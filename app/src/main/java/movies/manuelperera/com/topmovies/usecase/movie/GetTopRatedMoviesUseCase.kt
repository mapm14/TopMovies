package movies.manuelperera.com.topmovies.usecase.movie

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import manuelperera.com.base.client.transaction.Transaction
import manuelperera.com.base.usecase.UseCase
import movies.manuelperera.com.topmovies.domain.objects.domain.MovieAppDomain
import movies.manuelperera.com.topmovies.domain.service.MovieService

class GetTopRatedMoviesUseCase(private val movieService: MovieService) : UseCase<Observable<Transaction<MutableList<MovieAppDomain>>>> {

    override fun bind(): Observable<Transaction<MutableList<MovieAppDomain>>> =
            movieService.getTopRatedMovies().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).map { transaction ->
                Transaction(transaction.data?.movies, transaction.status)
            }

}