package movies.manuelperera.com.topmovies.domain.objects.api

import com.google.gson.annotations.SerializedName
import movies.manuelperera.com.topmovies.domain.objects.domain.MovieAppDomain

data class MovieApiResponse(
        @SerializedName("vote_count")
        val voteCount: Int,
        val id: Int,
        val video: Boolean,
        @SerializedName("vote_average")
        val voteAverage: Double,
        val title: String,
        val popularity: Double,
        @SerializedName("poster_path")
        val posterPath: String?,
        @SerializedName("original_language")
        val originalLanguage: String,
        @SerializedName("original_title")
        val originalTitle: String,
        @SerializedName("genre_ids")
        val genres: MutableList<Int>,
        @SerializedName("backdrop_path")
        val backdropPath: String?,
        val adult: Boolean,
        val overview: String?,
        @SerializedName("release_date")
        val releaseDate: String) {

    fun toAppDomain(): MovieAppDomain =
            MovieAppDomain(voteCount, id, voteAverage, title, posterPath ?: "", genres, backdropPath, overview, releaseDate)

}