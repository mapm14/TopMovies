package movies.manuelperera.com.topmovies.screen.toprated.section

import android.content.Context
import android.view.View
import io.reactivex.Completable
import io.reactivex.Observable
import kotlinx.android.synthetic.main.chrome_movie.view.*
import manuelperera.com.base.client.transaction.Transaction
import manuelperera.com.base.screen.presenter.recyclerview.InfiniteRecyclerViewAdapterPresenter
import manuelperera.com.base.screen.presenter.recyclerview.RecyclerViewAdapterItem
import movies.manuelperera.com.topmovies.R
import movies.manuelperera.com.topmovies.domain.objects.ui.MovieUI
import movies.manuelperera.com.topmovies.extensions.getErrorMessage
import movies.manuelperera.com.topmovies.usecase.movie.GetConfigUseCase
import movies.manuelperera.com.topmovies.usecase.movie.GetTopRatedMoviesUseCase
import movies.manuelperera.com.topmovies.usecase.movie.SetMovieSelectedUseCase
import movies.manuelperera.com.topmovies.usecase.movie.SetTopRatedMoviesPaginationUseCase
import movies.manuelperera.com.topmovies.view.widget.MovieChromeView

class TopRatedMoviesRecyclerAdapterPresenter(private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
                                             private val setMovieSelectedUseCase: SetMovieSelectedUseCase,
                                             private val setTopRatedMoviesPaginationUseCase: SetTopRatedMoviesPaginationUseCase,
                                             private val getConfigUseCase: GetConfigUseCase,
                                             context: Context) : InfiniteRecyclerViewAdapterPresenter<TopRatedMoviesRecyclerAdapterView, MovieUI>() {

    var errorMessage = context.getString(R.string.ups_error_message)
    var baseUrl = ""

    init {
        getImageBaseUrl()
    }

    override fun getLoadObservable(): Observable<Transaction<MutableList<MovieUI>>> =
            getTopRatedMoviesUseCase.bind().map { transaction ->
                view?.finishLoad()

                if (transaction.isSuccess() && transaction.data != null)
                    Transaction(transaction.data?.map { it.toUIModel() }?.toMutableList(), transaction.status)
                else {
                    errorMessage = getErrorMessage(transaction.errorBody, errorMessage)
                    Transaction(status = transaction.status, errorBody = transaction.errorBody)
                }
            }

    override fun getPaginationObservable(page: Int): Observable<Any> =
            setTopRatedMoviesPaginationUseCase.bind(SetTopRatedMoviesPaginationUseCase.Params(page))

    override fun getItemClickCompletable(viewClicked: View, data: MovieUI): Completable =
            setMovieSelectedUseCase.bind(SetMovieSelectedUseCase.Params(data)).doOnComplete {
                if (viewClicked is MovieChromeView)
                    view?.routeToMovieDetail(viewClicked.movieImageView)
                else
                    view?.routeToMovieDetail()
            }

    override fun getLoadingList(): MutableList<MovieUI> = mutableListOf(MovieUI(rType = RecyclerViewAdapterItem.Type.LOADING))

    override fun getNetworkErrorList(): MutableList<MovieUI> = mutableListOf(MovieUI(rType = RecyclerViewAdapterItem.Type.ERROR))

    override fun getFooterList(): MutableList<MovieUI> = mutableListOf(MovieUI(rType = RecyclerViewAdapterItem.Type.FOOTER))

    override fun getFullscreenLoadingList(): MutableList<MovieUI> = mutableListOf(MovieUI(rType = RecyclerViewAdapterItem.Type.FULLSCREEN_LOADING))

    override fun getFullscreenNetworkErrorList(): MutableList<MovieUI> = mutableListOf(MovieUI(rType = RecyclerViewAdapterItem.Type.FULLSCREEN_ERROR))

    fun clearPaginationAndReloadAdapter() =
            addSubscription(setTopRatedMoviesPaginationUseCase.bind(SetTopRatedMoviesPaginationUseCase.Params(1)).subscribe {
                bindReloadDataObservable(Observable.just(true))
            })

    private fun getImageBaseUrl() =
            addSubscription(getConfigUseCase.bind().subscribe { transaction ->
                transaction.data?.let {
                    baseUrl = it.images.getChromePosterSizeUrl()
                }
            })

}