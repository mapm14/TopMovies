package movies.manuelperera.com.topmovies.screen.toprated

import android.content.Context
import manuelperera.com.base.screen.presenter.Presenter
import movies.manuelperera.com.topmovies.R
import movies.manuelperera.com.topmovies.extensions.getErrorMessage
import movies.manuelperera.com.topmovies.usecase.movie.GetConfigUseCase

class TopRatedMoviesPresenter(private val getConfigUseCase: GetConfigUseCase,
                              context: Context) : Presenter<TopRatedMoviesView>() {

    var errorMessage: String = context.getString(R.string.ups_error_message)

    override fun init() {
        getConfig()
    }

    fun getConfig() {
        addSubscription(getConfigUseCase.bind().subscribe { transaction ->
            if (transaction.isSuccess())
                view?.onConfigLoaded()
            else
                view?.onLoadError(getErrorMessage(transaction.errorBody, errorMessage))
        })
    }

}