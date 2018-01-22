package movies.manuelperera.com.topmovies.view

import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.chrome_movie.view.*
import movies.manuelperera.com.topmovies.R
import movies.manuelperera.com.topmovies.domain.objects.ui.MovieUI
import movies.manuelperera.com.topmovies.extensions.loadUrl

class MovieChromeView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : CardView(context, attrs, defStyleAttr) {

    init {
        init()
    }

    private fun init() {
        inflate(context, R.layout.chrome_movie, this)
        layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
    }

    fun setMovieChrome(movie: MovieUI) {
        movieImageView.loadUrl(movie.posterPath)
        movieChromeVoteAverageTextView.text = movie.voteAverage.toString()
        movieChromeTitleTextView.text = movie.title
        requestLayout()
    }

}