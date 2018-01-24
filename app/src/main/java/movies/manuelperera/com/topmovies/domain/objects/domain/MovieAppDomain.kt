package movies.manuelperera.com.topmovies.domain.objects.domain

import movies.manuelperera.com.topmovies.domain.objects.ui.MovieUI

data class MovieAppDomain(
        val voteCount: Int = 0,
        val id: Int = 0,
        val voteAverage: Double = 0.0,
        val title: String = "",
        var posterPath: String = "",
        val genres: MutableList<Int> = mutableListOf(),
        var backdropPath: String? = "",
        val overview: String? = "",
        val releaseDate: String = "") {

    fun toUIModel(): MovieUI =
            MovieUI(voteCount, id, voteAverage, title, posterPath, genres, backdropPath
                    ?: "", overview, releaseDate)

}