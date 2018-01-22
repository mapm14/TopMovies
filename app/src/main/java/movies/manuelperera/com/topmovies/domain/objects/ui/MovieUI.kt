package movies.manuelperera.com.topmovies.domain.objects.ui

import manuelperera.com.base.screen.presenter.recyclerview.RecyclerViewAdapterItem
import java.util.*

data class MovieUI(
        val voteCount: Int = 0,
        val id: Int = 0,
        val voteAverage: Double = 0.0,
        val title: String = "",
        var posterPath: String = "",
        val genres: MutableList<Int> = mutableListOf(),
        val backdropPath: String = "",
        val overview: String = "",
        val releaseDate: Date = Date(),
        var rType: RecyclerViewAdapterItem.Type = RecyclerViewAdapterItem.Type.ITEM) : RecyclerViewAdapterItem {

    override fun setType(type: RecyclerViewAdapterItem.Type) {
        rType = type
    }

    override fun getType(): RecyclerViewAdapterItem.Type = rType

}