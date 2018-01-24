package movies.manuelperera.com.topmovies.view.widget

import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.chrome_detail_movie.view.*
import kotlinx.android.synthetic.main.item_network_error.view.*
import movies.manuelperera.com.topmovies.R
import movies.manuelperera.com.topmovies.domain.objects.ui.MovieDetailUI
import movies.manuelperera.com.topmovies.domain.objects.ui.MovieUI
import movies.manuelperera.com.topmovies.extensions.addCommas
import movies.manuelperera.com.topmovies.extensions.formatDate
import movies.manuelperera.com.topmovies.extensions.loadUrl
import movies.manuelperera.com.topmovies.extensions.removeBracketsAndCommas

class MovieDetailChromeView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : CardView(context, attrs, defStyleAttr) {

    var isMovieDetailLoaded = false

    init {
        init()
    }

    private fun init() {
        inflate(context, R.layout.chrome_detail_movie, this)
        layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
    }

    fun setMovieChromeWithMovieUI(movie: MovieUI, delegate: () -> Unit = {}) {
        movieImageView.loadUrl(movie.posterPath, delegate = { delegate() })
        movieChromeDetailVoteAverageTextView.text = movie.voteAverage.toString()
        movieChromeDetailTitleTextView.text = movie.title
        if (!movie.overview.isNullOrEmpty()) {
            movieChromeDetailOverviewLinearLayout.visibility = View.VISIBLE
            movieChromeDetailOverviewTextView.text = movie.overview
        } else
            movieChromeDetailOverviewLinearLayout.visibility = View.GONE

        requestLayout()
    }

    fun setMovieChromeWithMovieDetailUI(movie: MovieDetailUI) {
        isMovieDetailLoaded = true
        movieChromeDetailLoadingView.visibility = View.GONE
        movieChromeDetailNetworkErrorView.visibility = View.GONE
        movieChromeDetailValuesLinearLayout.visibility = View.VISIBLE

        val runtimeKeyValue = ItemKeyValue(context).config(context.getString(R.string.runtime),  context.getString(R.string.format_minutes, movie.runtime.toString()))
        movieChromeDetailValuesLinearLayout.addView(runtimeKeyValue)

        val releaseDateKeyValue = ItemKeyValue(context).config(context.getString(R.string.release_date), movie.releaseDate.formatDate())
        movieChromeDetailValuesLinearLayout.addView(releaseDateKeyValue)

        val budgetKeyValue = ItemKeyValue(context).config(context.getString(R.string.budget), context.getString(R.string.format_dollars, movie.budget.addCommas()))
        movieChromeDetailValuesLinearLayout.addView(budgetKeyValue)

        val revenueKeyValue = ItemKeyValue(context).config(context.getString(R.string.revenue), context.getString(R.string.format_dollars, movie.revenue.addCommas()))
        movieChromeDetailValuesLinearLayout.addView(revenueKeyValue)

        val adultKeyValue = ItemKeyValue(context).config(context.getString(R.string.adult), if (movie.adult) context.getString(R.string.yes) else context.getString(R.string.no))
        movieChromeDetailValuesLinearLayout.addView(adultKeyValue)

        if (!movie.homepage.isNullOrEmpty()) {
            val homepageKeyValue = ItemKeyValue(context).config(context.getString(R.string.homepage), movie.homepage ?: "")
            movieChromeDetailValuesLinearLayout.addView(homepageKeyValue)
        }

        val voteCountKeyValue = ItemKeyValue(context).config(context.getString(R.string.vote_count), movie.voteCount.addCommas())
        movieChromeDetailValuesLinearLayout.addView(voteCountKeyValue)

        val genresKeyValue = ItemKeyValue(context).config(context.getString(R.string.genres), movie.genres.map { it.name + "\n" }.toString().removeBracketsAndCommas())
        movieChromeDetailValuesLinearLayout.addView(genresKeyValue)

        val productionCompaniesKeyValue = ItemKeyValue(context).config(context.getString(R.string.production_companies), movie.productionCompanies.map { it.name + "\n" }.toString().removeBracketsAndCommas())
        movieChromeDetailValuesLinearLayout.addView(productionCompaniesKeyValue)

        val productionCountriesKeyValue = ItemKeyValue(context).config(context.getString(R.string.production_countries), movie.productionCountries.map { it.name + "\n" }.toString().removeBracketsAndCommas())
        movieChromeDetailValuesLinearLayout.addView(productionCountriesKeyValue)

        val spokenLanguagesKeyValue = ItemKeyValue(context).config(context.getString(R.string.spoken_languages), movie.spokenLanguages.map { it.name + "\n" }.toString().removeBracketsAndCommas())
        movieChromeDetailValuesLinearLayout.addView(spokenLanguagesKeyValue)
    }

    fun setError(errorMessage: String) {
        movieChromeDetailLoadingView.visibility = View.GONE
        movieChromeDetailValuesLinearLayout.visibility = View.GONE
        movieChromeDetailNetworkErrorView.visibility = View.VISIBLE
        networkErrorRetryTextView.text = errorMessage
    }

    fun setLoading() {
        movieChromeDetailValuesLinearLayout.visibility = View.GONE
        movieChromeDetailNetworkErrorView.visibility = View.GONE
        movieChromeDetailLoadingView.visibility = View.VISIBLE
    }

}