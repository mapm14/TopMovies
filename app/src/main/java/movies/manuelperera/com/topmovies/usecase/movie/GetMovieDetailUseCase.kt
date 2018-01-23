package movies.manuelperera.com.topmovies.usecase.movie

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import manuelperera.com.base.client.transaction.Transaction
import manuelperera.com.base.usecase.UseCaseParams
import manuelperera.com.base.usecase.UseCaseWithParams
import movies.manuelperera.com.topmovies.domain.objects.ui.MovieDetailUI
import movies.manuelperera.com.topmovies.domain.service.MovieService

class GetMovieDetailUseCase(private val movieService: MovieService) : UseCaseWithParams<Observable<Transaction<MovieDetailUI>>, GetMovieDetailUseCase.Params> {

    override fun bind(params: Params): Observable<Transaction<MovieDetailUI>> =
            movieService.getMovieDetail(params.movieId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).map { transaction ->
                Transaction(transaction.data?.toAppDomain(), transaction.status, transaction.errorBody)
            }

    class Params(val movieId: Int) : UseCaseParams()

}