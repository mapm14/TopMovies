package movies.manuelperera.com.topmovies.screen.toprated.section

import android.support.v4.view.ViewCompat
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import kotlinx.android.synthetic.main.chrome_movie.view.*
import kotlinx.android.synthetic.main.item_network_error.view.*
import manuelperera.com.base.screen.presenter.recyclerview.RecyclerViewAdapterItem
import movies.manuelperera.com.topmovies.R
import movies.manuelperera.com.topmovies.TopMoviesApp
import movies.manuelperera.com.topmovies.domain.objects.ui.MovieUI
import movies.manuelperera.com.topmovies.extensions.applyMargins
import movies.manuelperera.com.topmovies.extensions.inflate
import movies.manuelperera.com.topmovies.screen.toprated.TopRatedMoviesView
import movies.manuelperera.com.topmovies.screen.toprated.section.injection.DaggerTopRatedMoviesRecyclerAdapterComponent
import movies.manuelperera.com.topmovies.view.MovieChromeView
import movies.manuelperera.com.topmovies.view.viewholder.ErrorSectionViewHolder
import movies.manuelperera.com.topmovies.view.viewholder.FooterSectionViewHolder
import movies.manuelperera.com.topmovies.view.viewholder.LoadingSectionViewHolder
import movies.manuelperera.com.topmovies.view.viewholder.RecyclerViewViewHolder
import javax.inject.Inject

class TopRatedMoviesRecyclerAdapter(private val topRatedMoviesView: TopRatedMoviesView) : RecyclerView.Adapter<RecyclerViewViewHolder<MovieUI>>(), TopRatedMoviesRecyclerAdapterView {

    private lateinit var mRecyclerView: RecyclerView

    @Inject
    lateinit var topRatedMoviesRecyclerAdapterPresenter: TopRatedMoviesRecyclerAdapterPresenter

    init {
        DaggerTopRatedMoviesRecyclerAdapterComponent.builder().appComponent(TopMoviesApp.daggerAppComponent()).build().inject(this)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
        topRatedMoviesRecyclerAdapterPresenter.init(this)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView?) {
        super.onDetachedFromRecyclerView(recyclerView)
        topRatedMoviesRecyclerAdapterPresenter.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder<MovieUI>? =
            when (viewType) {
                RecyclerViewAdapterItem.Type.ITEM.ordinal -> ProductsExtendedViewHolder(MovieChromeView(parent.context), topRatedMoviesRecyclerAdapterPresenter)
                RecyclerViewAdapterItem.Type.FULLSCREEN_LOADING.ordinal -> LoadingSectionViewHolder((parent.inflate(R.layout.item_loading)))
                RecyclerViewAdapterItem.Type.LOADING.ordinal -> LoadingSectionViewHolder((parent.inflate(R.layout.item_loading_infinite)))
                RecyclerViewAdapterItem.Type.FOOTER.ordinal -> FooterSectionViewHolder(parent.inflate(R.layout.item_footer))
                RecyclerViewAdapterItem.Type.ERROR.ordinal -> ErrorSectionViewHolder((parent.inflate(R.layout.item_network_infinite_error)), { view ->
                    topRatedMoviesRecyclerAdapterPresenter.bindReloadDataObservable(view.networkErrorRetryButton.asObservable())
                })
                RecyclerViewAdapterItem.Type.FULLSCREEN_ERROR.ordinal -> ErrorSectionViewHolder((parent.inflate(R.layout.item_network_error)), { view ->
                    view.networkErrorRetryTextView.text = topRatedMoviesRecyclerAdapterPresenter.errorMessage
                    topRatedMoviesRecyclerAdapterPresenter.bindReloadDataObservable(view.networkErrorRetryButton.asObservable())
                })
                else -> ErrorSectionViewHolder((parent.inflate(R.layout.item_network_error)), { view ->
                    view.networkErrorRetryTextView.text = topRatedMoviesRecyclerAdapterPresenter.errorMessage
                    topRatedMoviesRecyclerAdapterPresenter.bindReloadDataObservable(view.networkErrorRetryButton.asObservable())
                })
            }

    override fun getRecyclerView(): RecyclerView = mRecyclerView

    override fun onBindViewHolder(holder: RecyclerViewViewHolder<MovieUI>, position: Int) {
        holder.configure(topRatedMoviesRecyclerAdapterPresenter.listData[position])
    }

    override fun getItemCount(): Int = topRatedMoviesRecyclerAdapterPresenter.listData.size

    override fun getItemViewType(position: Int): Int = topRatedMoviesRecyclerAdapterPresenter.listData[position].rType.ordinal

    override fun getDiffResultBinder(diffResult: DiffUtil.DiffResult): DiffUtil.DiffResult {
        diffResult.dispatchUpdatesTo(this@TopRatedMoviesRecyclerAdapter)
        return diffResult
    }

    override fun finishLoad() {
        topRatedMoviesView.getSwipeRefreshLayout().isRefreshing = false
    }

    override fun routeToMovieDetail(sharedImageView: ImageView?) {
        topRatedMoviesView.routeToMovieDetail(sharedImageView)
    }

    private class ProductsExtendedViewHolder(val view: View, val presenter: TopRatedMoviesRecyclerAdapterPresenter) : RecyclerViewViewHolder<MovieUI>(view) {
        override fun configure(item: MovieUI?) {
            item?.let { movie ->
                val movieChromeView: MovieChromeView = (view as MovieChromeView)
                movie.posterPath = presenter.baseUrl + movie.posterPath
                movieChromeView.setMovieChrome(movie)
                applyMargins(movieChromeView, 5f, 5f, 3f, 3f)
                presenter.bindItemClick(itemView, presenter.listData[adapterPosition])

                ViewCompat.setTransitionName(movieChromeView.movieImageView, movie.id.toString())
            }
        }
    }

    fun reloadAdapter() {
        topRatedMoviesRecyclerAdapterPresenter.listData.clear()
        topRatedMoviesRecyclerAdapterPresenter.resetRecyclerViewPagination()
        topRatedMoviesRecyclerAdapterPresenter.clearPaginationAndReloadAdapter()
    }

}