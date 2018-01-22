package movies.manuelperera.com.topmovies.usecase.movie

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import manuelperera.com.base.client.transaction.Transaction
import manuelperera.com.base.usecase.UseCase
import movies.manuelperera.com.topmovies.domain.objects.ui.MovieUI
import movies.manuelperera.com.topmovies.domain.service.MovieService

class GetMovieSelectedUseCase(private val movieService: MovieService) : UseCase<Observable<Transaction<MovieUI>>> {

    override fun bind(): Observable<Transaction<MovieUI>> =
            movieService.getMovie().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

}