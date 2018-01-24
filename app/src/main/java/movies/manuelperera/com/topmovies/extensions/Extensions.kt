package movies.manuelperera.com.topmovies.extensions

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.support.v4.widget.SwipeRefreshLayout
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import manuelperera.com.base.domain.model.ErrorBody
import movies.manuelperera.com.topmovies.R
import movies.manuelperera.com.topmovies.domain.objects.domain.ImagesAppDomain
import movies.manuelperera.com.topmovies.domain.objects.domain.MovieAppDomain
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

const val patternDateFromAPI = "yyyy-MM-dd"
const val patternDateOfApp = "dd/MM/yyyy"

fun ViewGroup.inflate(layoutResourceId: Int): View = LayoutInflater.from(context).inflate(layoutResourceId, this, false)

fun convertDpToPixel(dp: Float): Float = dp * (Resources.getSystem().displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)

fun convertPixelsToDp(px: Float): Float = px / (Resources.getSystem().displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)

fun String.formatDate(): String {
    val simpleDateFormatFromAPI = SimpleDateFormat(patternDateFromAPI, Locale.getDefault())
    val date = simpleDateFormatFromAPI.parse(this)
    val simpleDateFormatOfApp = SimpleDateFormat(patternDateOfApp, Locale.getDefault())
    return simpleDateFormatOfApp.format(date)
}

fun Activity.hideKeyboard() {
    currentFocus?.let {
        val inputMethodManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }
}

fun ImageView.loadUrl(url: String?, placeholder: Int = R.drawable.ic_film, delegate: () -> Unit = {}) {
    url?.let {
        val builder = Picasso.Builder(context)
        builder.listener { _, _, _ -> delegate() }

        builder.build()
                .load(url)
                .placeholder(placeholder)
                .into(this, object : Callback {
                    override fun onSuccess() {
                        delegate()
                    }

                    override fun onError() {
                        delegate()
                    }
                })
    }
}

fun SwipeRefreshLayout.setThemeColors() {
    setColorSchemeResources(
            R.color.colorAccent,
            R.color.colorAccentDark
    )
}

fun getErrorMessage(errorBody: ErrorBody?, defaultMessage: String): String =
        errorBody?.message ?: defaultMessage

fun applyMargins(view: View, topMargin: Float = 0f, bottomMargin: Float = 0f, marginStart: Float = 0f, marginEnd: Float = 0f): View {
    val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
    layoutParams.topMargin = convertDpToPixel(topMargin).toInt()
    layoutParams.marginStart = convertDpToPixel(marginStart).toInt()
    layoutParams.marginEnd = convertDpToPixel(marginEnd).toInt()
    layoutParams.bottomMargin = convertDpToPixel(bottomMargin).toInt()
    view.layoutParams = layoutParams
    return view
}

fun String.removeBracketsAndCommas() =
        this.replace(",", "").replace("[", "").replace("]", "")

fun Int.addCommas(): String {
    val formatter = NumberFormat.getInstance(Locale.US) as DecimalFormat
    formatter.applyPattern("#,###")
    return formatter.format(this)
}

fun addBaseUrlToMovieList(imagesAppDomain: ImagesAppDomain?, movies: MutableList<MovieAppDomain>?) {
    imagesAppDomain?.secureBaseUrl?.let { baseUrl ->
        (movies ?: mutableListOf())
                .filterNot { it.posterPath.contains(baseUrl) }
                .forEach { it.posterPath = imagesAppDomain.getChromePosterSizeUrl() + it.posterPath }
    }
}