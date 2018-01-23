package movies.manuelperera.com.topmovies.screen.moviedetail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_movie_detail.*
import movies.manuelperera.com.topmovies.R
import movies.manuelperera.com.topmovies.TopMoviesApp
import movies.manuelperera.com.topmovies.domain.objects.ui.MovieUI
import movies.manuelperera.com.topmovies.screen.moviedetail.injection.DaggerMovieDetailComponent
import movies.manuelperera.com.topmovies.screen.moviedetail.section.MovieDetailRecyclerAdapter
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
//        movieDetailPresenter.init(this)
        setUpToolbar()
        configRecycler()
    }

    private fun setUpToolbar() {
        setSupportActionBar(movieDetailToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }

    override fun onDestroy() {
//        movieDetailPresenter.clear()
        super.onDestroy()
    }

    override fun onLoadMovieSelected(movieUI: MovieUI?) {
        movieUI?.let {
            configRecycler()
        }
    }

    override fun finishEnterTransition() {
        supportStartPostponedEnterTransition()
    }

    private fun configRecycler() {
        similarMoviesRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MovieDetailActivity, LinearLayoutManager.HORIZONTAL, false)
            itemAnimator = null
            adapter = MovieDetailRecyclerAdapter(this@MovieDetailActivity)
        }
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(similarMoviesRecyclerView)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

}
