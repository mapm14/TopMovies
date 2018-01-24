package movies.manuelperera.com.topmovies.extensions

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat
import android.widget.ImageView
import movies.manuelperera.com.topmovies.screen.moviedetail.MovieDetailActivity

fun Activity.routeToMovieDetailActivity(sharedImageView: ImageView? = null) {
    val intent = Intent(this, MovieDetailActivity::class.java)

    if (sharedImageView != null) {
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, sharedImageView, ViewCompat.getTransitionName(sharedImageView))
        startActivity(intent, options.toBundle())
    } else
        startActivity(intent)
}

fun Activity.openWebBrowser(url: String) {
    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
}