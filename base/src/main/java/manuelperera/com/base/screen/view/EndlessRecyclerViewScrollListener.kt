package manuelperera.com.base.screen.view

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import io.reactivex.Observer

class EndlessRecyclerViewScrollListener(private val mLayoutManager: RecyclerView.LayoutManager, private val mObserver: Observer<in Int>, hasLoadingView: Boolean) : RecyclerView.OnScrollListener() {
    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private val visibleThreshold = 3
    // The current offset index of data you have loaded
    var currentPage = 1
        private set
    // The total number of items in the dataset after the last load
    private var previousTotalItemCount = 0
    // True if we are still waiting for the last set of data to load.
    var isLoading = true
        private set

    private val minTotalItemCount: Int = if (hasLoadingView) 1 else 0

    private fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (i in lastVisibleItemPositions.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }

    // This happens many times a second during a scroll, so be wary of the code you place here.
    // We are given a few useful parameters to help us work out if we need to load some more data,
    // but first we check if we are waiting for the previous load to finish.
    override fun onScrolled(view: RecyclerView?, dx: Int, dy: Int) {
        var lastVisibleItemPosition = 0
        val totalItemCount = mLayoutManager.itemCount

        when (mLayoutManager) {
            is StaggeredGridLayoutManager -> {
                val lastVisibleItemPositions = mLayoutManager.findLastVisibleItemPositions(null)
                // get maximum element within the list
                lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions)
            }
            is GridLayoutManager -> lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition()
            is LinearLayoutManager -> lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition()
        }

        // If the total item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state

        // If it’s still loading, we check to see if the dataset count has
        // changed, if so we conclude it has finished loading and update the current page
        // number and total item count.

        // If it isn’t currently loading, we check to see if we have breached
        // the visibleThreshold and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
        // threshold should reflect how many total columns there are too

        // If the total item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        if (totalItemCount < previousTotalItemCount) {
            currentPage = 1
            previousTotalItemCount = totalItemCount
            if (totalItemCount == 0) {
                isLoading = true
            }
        }

        // If it’s still loading, we check to see if the dataset count has
        // changed, if so we conclude it has finished loading and update the current page
        // number and total item count.
        if (isLoading && totalItemCount > previousTotalItemCount && totalItemCount - previousTotalItemCount > minTotalItemCount) {
            isLoading = false
            previousTotalItemCount = totalItemCount
        }

        // If it isn’t currently loading, we check to see if we have breached
        // the visibleThreshold and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
        // threshold should reflect how many total columns there are too
        if (!isLoading && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
            isLoading = true
            currentPage++
            mObserver.onNext(currentPage)
        }
    }

    fun reset() {
        currentPage = 1
        previousTotalItemCount = 0
        isLoading = true
    }

    fun setCurrentPageAndPreviousTotalItemCount(page: Int) {
        currentPage = page
    }
}