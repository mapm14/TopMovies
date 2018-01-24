package movies.manuelperera.com.topmovies.screen.moviedetail.section

import android.content.Context
import android.view.View
import io.reactivex.Completable
import io.reactivex.Observable
import manuelperera.com.base.client.transaction.Transaction
import manuelperera.com.base.screen.presenter.recyclerview.InfiniteRecyclerViewAdapterPresenter
import manuelperera.com.base.screen.presenter.recyclerview.RecyclerViewAdapterItem
import movies.manuelperera.com.topmovies.R
import movies.manuelperera.com.topmovies.domain.objects.ui.MovieUI
import movies.manuelperera.com.topmovies.extensions.getErrorMessage
import movies.manuelperera.com.topmovies.usecase.movie.GetConfigUseCase
import movies.manuelperera.com.topmovies.usecase.movie.GetMovieDetailUseCase
import movies.manuelperera.com.topmovies.usecase.movie.GetSimilarMoviesUseCase
import movies.manuelperera.com.topmovies.usecase.movie.SetSimilarMoviesPaginationUseCase
import movies.manuelperera.com.topmovies.view.widget.MovieDetailChromeView

class MovieDetailRecyclerAdapterPresenter(private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase,
                                          private val setSimilarMoviesPaginationUseCase: SetSimilarMoviesPaginationUseCase,
                                          private val getConfigUseCase: GetConfigUseCase,
                                          private val getMovieDetailUseCase: GetMovieDetailUseCase,
                                          context: Context) : InfiniteRecyclerViewAdapterPresenter<MovieDetailRecyclerAdapterView, MovieUI>() {

    var errorMessage: String = context.getString(R.string.ups_error_message)
    var baseUrl = ""

    init {
        getImageBaseUrl()
    }

    override fun getLoadObservable(): Observable<Transaction<MutableList<MovieUI>>> =
            getSimilarMoviesUseCase.bind().map { transaction ->
                if (transaction.isSuccess() && transaction.data != null)
                    Transaction(transaction.data?.map { it.toUIModel() }?.toMutableList(), transaction.status)
                else {
                    errorMessage = getErrorMessage(transaction.errorBody, errorMessage)
                    Transaction(status = transaction.status, errorBody = transaction.errorBody)
                }
            }

    override fun getPaginationObservable(page: Int): Observable<Any> =
            setSimilarMoviesPaginationUseCase.bind(SetSimilarMoviesPaginationUseCase.Params(page))

    override fun getItemClickCompletable(viewClicked: View, data: MovieUI): Completable =
            Completable.complete()

    override fun getLoadingList(): MutableList<MovieUI> = mutableListOf(MovieUI(rType = RecyclerViewAdapterItem.Type.LOADING))

    override fun getNetworkErrorList(): MutableList<MovieUI> = mutableListOf(MovieUI(rType = RecyclerViewAdapterItem.Type.ERROR))

    override fun getFooterList(): MutableList<MovieUI> = mutableListOf(MovieUI(rType = RecyclerViewAdapterItem.Type.FOOTER))

    override fun getFullscreenLoadingList(): MutableList<MovieUI> = mutableListOf(MovieUI(rType = RecyclerViewAdapterItem.Type.FULLSCREEN_LOADING))

    override fun getFullscreenNetworkErrorList(): MutableList<MovieUI> = mutableListOf(MovieUI(rType = RecyclerViewAdapterItem.Type.FULLSCREEN_ERROR))

    fun clearPaginationAndReloadAdapter(reloadAdapter: Boolean) =
            addSubscription(setSimilarMoviesPaginationUseCase.bind(SetSimilarMoviesPaginationUseCase.Params(1)).subscribe {
                if (reloadAdapter)
                    bindReloadDataObservable(Observable.just(true))
            })

    private fun getImageBaseUrl() =
            addSubscription(getConfigUseCase.bind().subscribe { transaction ->
                transaction.data?.let {
                    baseUrl = it.images.getFullPosterSizeUrl()
                }
            })

    fun getMovieDetail(chromeView: MovieDetailChromeView, movieId: Int) =
            addSubscription(getMovieDetailUseCase.bind(GetMovieDetailUseCase.Params(movieId)).subscribe { transaction ->
                if (transaction.isSuccess())
                    transaction.data?.let { movieDetail ->
                        view?.onLoadMovieDetail(chromeView, movieDetail)
                    }
                else
                    view?.onLoadMovieDetailError(chromeView, movieId, getErrorMessage(transaction.errorBody, errorMessage))
            })

}