package manuelperera.com.base.screen.view

import android.support.v7.widget.RecyclerView
import io.reactivex.Observable
import io.reactivex.Observer

class EndlessRecyclerViewScrollObservable(private val mRecyclerView: RecyclerView, private val mHasLoadingView: Boolean) : Observable<Int>() {
    private lateinit var endlessRecyclerViewScrollListener: EndlessRecyclerViewScrollListener

    fun recyclerViewCurrentPage(): Int =
            endlessRecyclerViewScrollListener.currentPage

    fun isLoading(): Boolean =
            endlessRecyclerViewScrollListener.isLoading

    override fun subscribeActual(observer: Observer<in Int>) {
        endlessRecyclerViewScrollListener = EndlessRecyclerViewScrollListener(mRecyclerView.layoutManager, observer, mHasLoadingView)
        mRecyclerView.addOnScrollListener(endlessRecyclerViewScrollListener)
    }

    fun resetEndlessRecyclerViewScrollPagination() {
        endlessRecyclerViewScrollListener.reset()
    }

    fun setEndlessRecyclerViewScrollPagination(page: Int) {
        endlessRecyclerViewScrollListener.setCurrentPageAndPreviousTotalItemCount(page)
    }

}