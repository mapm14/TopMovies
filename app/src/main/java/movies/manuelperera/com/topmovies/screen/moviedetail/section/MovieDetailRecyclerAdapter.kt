package movies.manuelperera.com.topmovies.screen.moviedetail.section

import android.os.Handler
import android.support.v4.view.ViewCompat
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.chrome_detail_movie.view.*
import kotlinx.android.synthetic.main.item_network_error.view.*
import manuelperera.com.base.screen.presenter.recyclerview.RecyclerViewAdapterItem
import movies.manuelperera.com.topmovies.R
import movies.manuelperera.com.topmovies.TopMoviesApp
import movies.manuelperera.com.topmovies.domain.objects.ui.MovieDetailUI
import movies.manuelperera.com.topmovies.domain.objects.ui.MovieUI
import movies.manuelperera.com.topmovies.extensions.inflate
import movies.manuelperera.com.topmovies.screen.moviedetail.MovieDetailView
import movies.manuelperera.com.topmovies.screen.moviedetail.section.injection.DaggerMovieDetailRecyclerAdapterComponent
import movies.manuelperera.com.topmovies.view.viewholder.ErrorSectionViewHolder
import movies.manuelperera.com.topmovies.view.viewholder.FooterSectionViewHolder
import movies.manuelperera.com.topmovies.view.viewholder.LoadingSectionViewHolder
import movies.manuelperera.com.topmovies.view.viewholder.RecyclerViewViewHolder
import movies.manuelperera.com.topmovies.view.widget.MovieDetailChromeView
import javax.inject.Inject

class MovieDetailRecyclerAdapter(private val movieDetailView: MovieDetailView) : RecyclerView.Adapter<RecyclerViewViewHolder<MovieUI>>(), MovieDetailRecyclerAdapterView {

    private lateinit var mRecyclerView: RecyclerView

    @Inject
    lateinit var movieDetailRecyclerAdapterPresenter: MovieDetailRecyclerAdapterPresenter

    init {
        DaggerMovieDetailRecyclerAdapterComponent.builder().appComponent(TopMoviesApp.daggerAppComponent()).build().inject(this)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
        movieDetailRecyclerAdapterPresenter.init(this)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView?) {
        super.onDetachedFromRecyclerView(recyclerView)
        movieDetailRecyclerAdapterPresenter.clearPaginationAndReloadAdapter(false)
        movieDetailRecyclerAdapterPresenter.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder<MovieUI>? =
            when (viewType) {
                RecyclerViewAdapterItem.Type.ITEM.ordinal -> MovieDetailViewHolder(MovieDetailChromeView(parent.context), movieDetailRecyclerAdapterPresenter, movieDetailView)
                RecyclerViewAdapterItem.Type.FULLSCREEN_LOADING.ordinal -> LoadingSectionViewHolder((parent.inflate(R.layout.item_loading)))
                RecyclerViewAdapterItem.Type.LOADING.ordinal -> LoadingSectionViewHolder((parent.inflate(R.layout.item_loading)))
                RecyclerViewAdapterItem.Type.FOOTER.ordinal -> FooterSectionViewHolder(parent.inflate(R.layout.item_footer))
                RecyclerViewAdapterItem.Type.ERROR.ordinal -> ErrorSectionViewHolder((parent.inflate(R.layout.item_network_error)), { view ->
                    showErrorAndBindReload(view)
                })
                RecyclerViewAdapterItem.Type.FULLSCREEN_ERROR.ordinal -> ErrorSectionViewHolder((parent.inflate(R.layout.item_network_error)), { view ->
                    showErrorAndBindReload(view)
                })
                else -> ErrorSectionViewHolder((parent.inflate(R.layout.item_network_error)), { view ->
                    showErrorAndBindReload(view)
                })
            }

    private fun showErrorAndBindReload(view: View) {
        view.networkErrorRetryTextView.text = movieDetailRecyclerAdapterPresenter.errorMessage
        movieDetailRecyclerAdapterPresenter.bindReloadDataObservable(view.networkErrorRetryButton.asObservable())
    }

    override fun getRecyclerView(): RecyclerView = mRecyclerView

    override fun onBindViewHolder(holder: RecyclerViewViewHolder<MovieUI>, position: Int) {
        holder.configure(movieDetailRecyclerAdapterPresenter.listData[position])
    }

    override fun getItemCount(): Int = movieDetailRecyclerAdapterPresenter.listData.size

    override fun getItemViewType(position: Int): Int = movieDetailRecyclerAdapterPresenter.listData[position].rType.ordinal

    override fun getDiffResultBinder(diffResult: DiffUtil.DiffResult): DiffUtil.DiffResult {
        diffResult.dispatchUpdatesTo(this@MovieDetailRecyclerAdapter)
        return diffResult
    }

    private class MovieDetailViewHolder(val view: View, val presenter: MovieDetailRecyclerAdapterPresenter, val movieDetailView: MovieDetailView) : RecyclerViewViewHolder<MovieUI>(view) {
        override fun configure(item: MovieUI?) {
            if (item != null) {
                val movieDetailChromeView: MovieDetailChromeView = view as MovieDetailChromeView

                if (adapterPosition == 0) {
                    ViewCompat.setTransitionName(movieDetailChromeView.movieImageView, item.id.toString())
                    Handler().post {
                        movieDetailChromeView.setMovieChromeWithMovieUI(item, delegate = { movieDetailView.finishEnterTransition() })
                    }
                } else {
                    ViewCompat.setTransitionName(movieDetailChromeView.movieImageView, "NO_TRANSITION")
                    movieDetailChromeView.setMovieChromeWithMovieUI(item)
                }

                presenter.getMovieDetail(movieDetailChromeView, item.id)
                movieDetailChromeView.scrollToTop()
            }
        }
    }

    override fun onLoadMovieDetail(chromeView: MovieDetailChromeView, movieDetailUI: MovieDetailUI) {
        chromeView.setMovieChromeWithMovieDetailUI(movieDetailUI).doOnComplete {
            chromeView.homepageTextView?.let { textView ->
                RxView.clicks(textView).subscribe {
                    movieDetailView.openWeb(textView.text.toString())
                }
            }
        }.subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe()
    }

    override fun onLoadMovieDetailError(chromeView: MovieDetailChromeView, movieId: Int, errorMessage: String) {
        chromeView.setError(errorMessage)
        chromeView.networkErrorRetryButton.asObservable().subscribe {
            chromeView.setLoading()
            movieDetailRecyclerAdapterPresenter.getMovieDetail(chromeView, movieId)
        }
    }

}