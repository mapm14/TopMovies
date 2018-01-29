package movies.manuelperera.com.topmovies.view.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View

abstract class RecyclerViewViewHolder<in T>(view: View) : RecyclerView.ViewHolder(view) {

    abstract fun configure(item: T? = null)

}