package movies.manuelperera.com.topmovies.screen.moviedetail

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_movie_detail.*
import movies.manuelperera.com.topmovies.R
import movies.manuelperera.com.topmovies.extensions.openWebBrowser
import movies.manuelperera.com.topmovies.screen.moviedetail.section.MovieDetailRecyclerAdapter

class MovieDetailActivity : AppCompatActivity(), MovieDetailView {

    private var alreadyPostponed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        supportPostponeEnterTransition()
        Handler().postDelayed({
            finishEnterTransition()
        }, 1500)
        setUpToolbar()
        configRecycler()
    }

    override fun onDestroy() {
        similarMoviesRecyclerView.adapter = null
        super.onDestroy()
    }

    override fun finishEnterTransition() {
        if (!alreadyPostponed) {
            alreadyPostponed = true
            supportStartPostponedEnterTransition()
        }
    }

    private fun setUpToolbar() {
        setSupportActionBar(movieDetailToolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun configRecycler() {
        similarMoviesRecyclerView.isNestedScrollingEnabled = true
        similarMoviesRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MovieDetailActivity, LinearLayoutManager.HORIZONTAL, false)
            itemAnimator = null
            adapter = MovieDetailRecyclerAdapter(this@MovieDetailActivity)
        }
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(similarMoviesRecyclerView)
    }

    override fun onBackArrowClick() {
        onBackPressed()
    }

    override fun openWeb(url: String) {
        openWebBrowser(url)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

}
