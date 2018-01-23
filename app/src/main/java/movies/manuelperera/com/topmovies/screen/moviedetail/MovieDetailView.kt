package movies.manuelperera.com.topmovies.screen.moviedetail

import manuelperera.com.base.screen.presenter.PresenterView
import movies.manuelperera.com.topmovies.domain.objects.ui.MovieUI

interface MovieDetailView : PresenterView {

    fun onLoadMovieSelected(movieUI: MovieUI?)

    fun finishEnterTransition()

}