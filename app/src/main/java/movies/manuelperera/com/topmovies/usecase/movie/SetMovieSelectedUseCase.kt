package movies.manuelperera.com.topmovies.usecase.movie

import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import manuelperera.com.base.usecase.UseCaseParams
import manuelperera.com.base.usecase.UseCaseWithParams
import movies.manuelperera.com.topmovies.domain.objects.ui.MovieUI
import movies.manuelperera.com.topmovies.domain.service.MovieService

class SetMovieSelectedUseCase(private val movieService: MovieService) : UseCaseWithParams<Completable, SetMovieSelectedUseCase.Params> {

    override fun bind(params: Params): Completable =
            movieService.setMovie(params.movie).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    class Params(val movie: MovieUI) : UseCaseParams()

}