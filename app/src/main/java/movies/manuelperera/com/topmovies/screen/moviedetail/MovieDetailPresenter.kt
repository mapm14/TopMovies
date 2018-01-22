package movies.manuelperera.com.topmovies.screen.moviedetail

import manuelperera.com.base.screen.presenter.Presenter
import movies.manuelperera.com.topmovies.usecase.movie.GetConfigUseCase
import movies.manuelperera.com.topmovies.usecase.movie.GetMovieSelectedUseCase

class MovieDetailPresenter(private val getMovieSelectedUseCase: GetMovieSelectedUseCase,
                           private val getConfigUseCase: GetConfigUseCase) : Presenter<MovieDetailView>() {

    var baseUrl = ""

    override fun init() {
        getImageBaseUrl()
        getMovieSelected()
    }

    private fun getMovieSelected() {
        addSubscription(getMovieSelectedUseCase.bind().subscribe { transaction ->
            view?.onLoadMovieSelected(transaction.data)
        })
    }

    private fun getImageBaseUrl() =
            addSubscription(getConfigUseCase.bind().subscribe { transaction ->
                transaction.data?.let {
                    baseUrl = it.images.getFullPosterSizeUrl()
                }
            })

}