package movies.manuelperera.com.topmovies.screen.moviedetail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import kotlinx.android.synthetic.main.activity_movie_detail.*
import movies.manuelperera.com.topmovies.R
import movies.manuelperera.com.topmovies.screen.moviedetail.section.MovieDetailRecyclerAdapter

class MovieDetailActivity : AppCompatActivity(), MovieDetailView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        supportPostponeEnterTransition()
        configRecycler()
    }

    override fun onDestroy() {
        similarMoviesRecyclerView.adapter = null
        super.onDestroy()
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

    override fun onBackArrowClick() {
        onBackPressed()
    }

}
