package movies.manuelperera.com.topmovies.screen.toprated

import android.support.v4.widget.SwipeRefreshLayout
import android.widget.ImageView
import manuelperera.com.base.screen.presenter.PresenterView

interface TopRatedMoviesView : PresenterView {

    fun getSwipeRefreshLayout() : SwipeRefreshLayout

    fun onConfigLoaded()

    fun onLoadError(error: String)

    fun routeToMovieDetail(sharedImageView: ImageView?)

}