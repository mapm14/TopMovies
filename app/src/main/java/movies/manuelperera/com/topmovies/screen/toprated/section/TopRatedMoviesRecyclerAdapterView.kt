package movies.manuelperera.com.topmovies.screen.toprated.section

import android.widget.ImageView
import manuelperera.com.base.screen.presenter.recyclerview.InfiniteRecyclerViewAdapterPresenterView

interface TopRatedMoviesRecyclerAdapterView : InfiniteRecyclerViewAdapterPresenterView {

    fun finishLoad()

    fun routeToMovieDetail(sharedImageView: ImageView? = null)

}