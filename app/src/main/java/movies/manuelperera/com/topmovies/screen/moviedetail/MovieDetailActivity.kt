package movies.manuelperera.com.topmovies.screen.moviedetail

import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import kotlinx.android.synthetic.main.activity_movie_detail.*
import movies.manuelperera.com.topmovies.R
import movies.manuelperera.com.topmovies.TopMoviesApp
import movies.manuelperera.com.topmovies.domain.objects.ui.MovieUI
import movies.manuelperera.com.topmovies.extensions.loadUrl
import movies.manuelperera.com.topmovies.screen.moviedetail.injection.DaggerMovieDetailComponent
import javax.inject.Inject

class MovieDetailActivity : AppCompatActivity(), MovieDetailView {

    @Inject
    lateinit var movieDetailPresenter: MovieDetailPresenter

    init {
        DaggerMovieDetailComponent.builder().appComponent(TopMoviesApp.daggerAppComponent()).build().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        supportPostponeEnterTransition()
        movieDetailPresenter.init(this)
    }

    override fun onDestroy() {
        movieDetailPresenter.clear()
        super.onDestroy()
    }

    override fun onLoadMovieSelected(movieUI: MovieUI?) {
        movieUI?.let { movie ->
            ViewCompat.setTransitionName(movieImageView, movie.id.toString())
            movieImageView.loadUrl(movieDetailPresenter.baseUrl + movie.posterPath, delegate = { supportStartPostponedEnterTransition() })
            movieDetailTitleTextView.text = movie.title
        }
    }

    fun configRecycler() {
//        productsRecyclerView.apply {
//            layoutManager = LinearLayoutManager(this@ProductsLandscapeActivity, LinearLayoutManager.HORIZONTAL, false)
//            itemAnimator = null
//            adapter = ProductsLandscapeActivityRecyclerAdapter(this@ProductsLandscapeActivity)
//        }
//        val snapHelper = PagerSnapHelper()
//        snapHelper.attachToRecyclerView(productsRecyclerView)
    }

}
