package movies.manuelperera.com.topmovies.view.widget

import android.content.Context
import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.util.AttributeSet
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            elevation = 10f
        cardBackgroundColor = ContextCompat.getColorStateList(context, R.color.backgroundMovieChrome)
    }

    fun setMovieChrome(movie: MovieUI) {
        movieImageView.loadUrl(movie.posterPath)
        movieChromeVoteAverageTextView.text = movie.voteAverage.toString()
        requestLayout()
    }

}