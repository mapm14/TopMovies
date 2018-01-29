package manuelperera.com.base.screen.presenter.recyclerview

import android.support.v7.widget.RecyclerView

interface InfiniteRecyclerViewAdapterPresenterView : RecyclerViewAdapterPresenterView {

    fun getRecyclerView(): RecyclerView

}