package movies.manuelperera.com.topmovies.view.viewholder

import android.view.View

class ErrorSectionViewHolder<in T>(val view: View, private val reloadAction: (View) -> Unit) : RecyclerViewViewHolder<T>(view) {

    override fun configure(item: T?) {
        reloadAction(view)
    }

}