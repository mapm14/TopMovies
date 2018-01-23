package movies.manuelperera.com.topmovies.screen.toprated

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_top_rated_movies.*
import kotlinx.android.synthetic.main.item_network_error.view.*
import manuelperera.com.base.screen.presenter.recyclerview.RecyclerViewAdapterItem
import movies.manuelperera.com.topmovies.R
import movies.manuelperera.com.topmovies.TopMoviesApp
import movies.manuelperera.com.topmovies.extensions.setThemeColors
import movies.manuelperera.com.topmovies.screen.moviedetail.MovieDetailActivity
import movies.manuelperera.com.topmovies.screen.toprated.injection.DaggerTopRatedMoviesComponent
import movies.manuelperera.com.topmovies.screen.toprated.section.TopRatedMoviesRecyclerAdapter
import javax.inject.Inject


class TopRatedMoviesActivity : AppCompatActivity(), TopRatedMoviesView {

    @Inject
    lateinit var topRatedMoviesPresenter: TopRatedMoviesPresenter

    init {
        DaggerTopRatedMoviesComponent.builder().appComponent(TopMoviesApp.daggerAppComponent()).build().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_rated_movies)
        topRatedMoviesPresenter.init(this)
        bindReloadButtonClick()
    }

    private fun bindReloadButtonClick() {
        topRatedMoviesNetworkErrorView.networkErrorRetryButton.asObservable().subscribe {
            topRatedMoviesNetworkErrorView.visibility = View.GONE
            topRatedMoviesSwipeRefresh.visibility = View.GONE
            topRatedMoviesLoadingView.visibility = View.VISIBLE
            topRatedMoviesPresenter.getConfig()
        }
    }

    private fun configRecycler() {
        topRatedMoviesRecyclerView?.apply {
            val topRatedMoviesRecyclerAdapter = TopRatedMoviesRecyclerAdapter(this@TopRatedMoviesActivity)
            val gridLayoutManager = GridLayoutManager(this@TopRatedMoviesActivity, 3)
            val mSpanSizeLookup: GridLayoutManager.SpanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (position <= topRatedMoviesRecyclerAdapter.topRatedMoviesRecyclerAdapterPresenter.listData.size - 1) {
                        val type = topRatedMoviesRecyclerAdapter.topRatedMoviesRecyclerAdapterPresenter.listData[position].rType
                        if (type == RecyclerViewAdapterItem.Type.LOADING || type == RecyclerViewAdapterItem.Type.ERROR ||
                                type == RecyclerViewAdapterItem.Type.FOOTER || type == RecyclerViewAdapterItem.Type.FULLSCREEN_LOADING ||
                                type == RecyclerViewAdapterItem.Type.FULLSCREEN_ERROR || type == RecyclerViewAdapterItem.Type.HEADER) {
                            3
                        } else 1
                    } else 1
                }
            }
            gridLayoutManager.spanSizeLookup = mSpanSizeLookup

            itemAnimator = null
            layoutManager = gridLayoutManager
            adapter = topRatedMoviesRecyclerAdapter
        }

        topRatedMoviesSwipeRefresh.setThemeColors()

        topRatedMoviesSwipeRefresh.setOnRefreshListener {
            if (topRatedMoviesRecyclerView?.adapter is TopRatedMoviesRecyclerAdapter)
                (topRatedMoviesRecyclerView.adapter as TopRatedMoviesRecyclerAdapter).reloadAdapter()
        }
    }

    override fun onDestroy() {
        topRatedMoviesPresenter.clear()
        topRatedMoviesRecyclerView.adapter = null
        super.onDestroy()
    }

    override fun getSwipeRefreshLayout(): SwipeRefreshLayout =
            topRatedMoviesSwipeRefresh

    override fun onConfigLoaded() {
        topRatedMoviesLoadingView.visibility = View.GONE
        topRatedMoviesNetworkErrorView.visibility = View.GONE
        topRatedMoviesSwipeRefresh.visibility = View.VISIBLE
        configRecycler()
    }

    override fun onLoadError(error: String) {
        topRatedMoviesLoadingView.visibility = View.GONE
        topRatedMoviesSwipeRefresh.visibility = View.GONE
        topRatedMoviesNetworkErrorView.visibility = View.VISIBLE
        topRatedMoviesNetworkErrorView.networkErrorRetryTextView.text = error
    }

    override fun routeToMovieDetail(sharedImageView: ImageView?) {
        val intent = Intent(this, MovieDetailActivity::class.java)

        if (sharedImageView != null) {
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, sharedImageView, ViewCompat.getTransitionName(sharedImageView))
            startActivity(intent, options.toBundle())
        } else
            startActivity(intent)
    }
}
