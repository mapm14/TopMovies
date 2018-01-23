package movies.manuelperera.com.topmovies.usecase.movie

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import manuelperera.com.base.usecase.UseCaseParams
import manuelperera.com.base.usecase.UseCaseWithParams
import movies.manuelperera.com.topmovies.domain.service.MovieService

class SetSimilarMoviesPaginationUseCase(private val movieService: MovieService) : UseCaseWithParams<Observable<Any>, SetSimilarMoviesPaginationUseCase.Params> {

    override fun bind(params: Params): Observable<Any> =
            movieService.setSimilarMoviesPagination(params.page).subscribeOn(Schedulers.io())

    class Params(val page: Int) : UseCaseParams()

}