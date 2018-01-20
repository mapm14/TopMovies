package manuelperera.com.base.screen.presenter.recyclerview

import android.support.v7.util.DiffUtil
import io.reactivex.Observable
import io.reactivex.functions.Function
import manuelperera.com.base.screen.view.EndlessRecyclerViewScrollObservable
import manuelperera.com.base.screen.view.RecyclerViewDiffUtilCallback
import java.util.*

abstract class InfiniteRecyclerViewAdapterPresenter<V : InfiniteRecyclerViewAdapterPresenterView, T : RecyclerViewAdapterItem> : RecyclerViewAdapterPresenter<V, T>() {

    private var mFullscreenLoadingList: List<T> = listOf()
    private var mFullscreenNetworkErrorList: List<T> = listOf()
    private var mFooterList: List<T> = listOf()

    private lateinit var endlessRecyclerViewScrollObservable: EndlessRecyclerViewScrollObservable

    override fun initialize() {
        mFullscreenLoadingList = getFullscreenLoadingList()

        mFullscreenNetworkErrorList = getFullscreenNetworkErrorList()

        mFooterList = getFooterList()

        if (hasFullscreenLoadingView()) {
            listData.removeAt(listData.size - 1)
            listData.addAll(mFullscreenLoadingList)
        }

        bindEndlessRecyclerViewScrollObservable()
    }

    private fun bindEndlessRecyclerViewScrollObservable() {
        view?.getRecyclerView()?.let { recyclerView ->
            endlessRecyclerViewScrollObservable = EndlessRecyclerViewScrollObservable(recyclerView, hasLoadingView())
            addSubscription(endlessRecyclerViewScrollObservable
                    .flatMap(setPagination())
                    .flatMap({ loadLoadingData() })
                    .flatMap(calculateRecyclerViewDiffs())
                    .doOnNext(showResults())
                    .flatMap({ load() })
                    .subscribe(showResults()))
        }
    }

    private fun setPagination(): Function<Int, Observable<Any>> =
            Function { page -> getPaginationObservable(page) }

    private fun hasFullscreenLoadingView(): Boolean =
            mFullscreenLoadingList.isNotEmpty() && mFullscreenLoadingList.isNotEmpty()

    private fun hasFullscreenNetworkErrorView(): Boolean =
            mFullscreenNetworkErrorList.isNotEmpty() && mFullscreenNetworkErrorList.isNotEmpty()

    private fun hasFooterView(): Boolean =
            mFooterList.isNotEmpty() && mFooterList.isNotEmpty()

    private fun getLastItemType(): RecyclerViewAdapterItem.Type =
            listData[listData.size - 1].getType()

    fun calculateRecyclerViewDiffDiffResult(newData: List<T>?): DiffUtil.DiffResult {
        val oldList = LinkedList<T>(listData)

        if (listData.isNotEmpty()) {
            if (getLastItemType() == RecyclerViewAdapterItem.Type.FULLSCREEN_LOADING ||
                    getLastItemType() == RecyclerViewAdapterItem.Type.LOADING ||
                    getLastItemType() == RecyclerViewAdapterItem.Type.FULLSCREEN_ERROR ||
                    getLastItemType() == RecyclerViewAdapterItem.Type.ERROR ||
                    getLastItemType() == RecyclerViewAdapterItem.Type.FOOTER ||
                    getLastItemType() == RecyclerViewAdapterItem.Type.EMPTY) {
                listData.removeAt(listData.size - 1)
            }
        }

        if (newData != null && newData.isNotEmpty()) {
            listData.addAll(newData)
        } else {
            if (hasFooterView()) {
                listData.addAll(mFooterList)
            }
        }

        if (listData.size == 1) {
            val item = listData[0]

            if (item.getType() == RecyclerViewAdapterItem.Type.LOADING) {
                if (hasFullscreenLoadingView()) {
                    if (listData.size > 0)
                        listData.removeAt(listData.size - 1)
                    listData.addAll(mFullscreenLoadingList)
                }
            }

            if (item.getType() == RecyclerViewAdapterItem.Type.ERROR) {
                if (hasFullscreenNetworkErrorView()) {
                    listData.removeAt(listData.size - 1)
                    listData.addAll(mFullscreenNetworkErrorList)
                }
            }
        }

        return DiffUtil.calculateDiff(RecyclerViewDiffUtilCallback(oldList, listData))
    }

    abstract fun getFullscreenLoadingList(): List<T>

    abstract fun getFullscreenNetworkErrorList(): List<T>

    abstract fun getFooterList(): List<T>

    abstract fun getPaginationObservable(page: Int): Observable<Any>

    fun resetRecyclerViewPagination() {
        endlessRecyclerViewScrollObservable.resetEndlessRecyclerViewScrollPagination()
    }

    fun setRecyclerViewPagination(page: Int) {
        endlessRecyclerViewScrollObservable.setEndlessRecyclerViewScrollPagination(page)
    }

    fun getRecyclerViewCurrentPage(): Int =
            endlessRecyclerViewScrollObservable.recyclerViewCurrentPage()

    fun isLoading(): Boolean =
            endlessRecyclerViewScrollObservable.isLoading()

}