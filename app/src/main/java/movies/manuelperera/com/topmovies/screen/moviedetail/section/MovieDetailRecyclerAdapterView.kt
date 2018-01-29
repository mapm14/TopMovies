package movies.manuelperera.com.topmovies.screen.moviedetail.section

import manuelperera.com.base.screen.presenter.recyclerview.InfiniteRecyclerViewAdapterPresenterView
import movies.manuelperera.com.topmovies.domain.objects.ui.MovieDetailUI
import movies.manuelperera.com.topmovies.view.widget.MovieDetailChromeView

interface MovieDetailRecyclerAdapterView : InfiniteRecyclerViewAdapterPresenterView {

    fun onLoadMovieDetail(chromeView: MovieDetailChromeView, movieDetailUI: MovieDetailUI)

    fun onLoadMovieDetailError(chromeView: MovieDetailChromeView, movieId: Int, errorMessage: String)

}