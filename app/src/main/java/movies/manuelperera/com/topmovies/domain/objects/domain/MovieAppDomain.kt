package movies.manuelperera.com.topmovies.domain.objects.domain

import movies.manuelperera.com.topmovies.domain.objects.ui.MovieUI
import java.util.*

data class MovieAppDomain(
        val voteCount: Int,
        val id: Int,
        val voteAverage: Double,
        val title: String,
        val posterPath: String?,
        val genres: MutableList<Int>,
        val backdropPath: String?,
        val overview: String?,
        val releaseDate: Date) {

    fun toUIModel(): MovieUI =
            MovieUI(voteCount, id, voteAverage, title, posterPath ?: "", genres, backdropPath ?: "", overview, releaseDate)

}