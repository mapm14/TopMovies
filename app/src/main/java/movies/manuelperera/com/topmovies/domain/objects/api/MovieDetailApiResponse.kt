package movies.manuelperera.com.topmovies.domain.objects.api

import com.google.gson.annotations.SerializedName
import movies.manuelperera.com.topmovies.domain.objects.domain.MovieDetailAppDomain

data class MovieDetailApiResponse(
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
        @SerializedName("backdrop_path")
        val backdropPath: String?,
        val adult: Boolean,
        val overview: String?,
        @SerializedName("release_date")
        val releaseDate: String,
        val budget: Int,
        val homepage: String?,
        val populatiry: Double,
        @SerializedName("imdb_id")
        val imdbId: String?,
        val revenue: Int,
        val runtime: Int?,
        val status: String,
        val tagline: String?,
        @SerializedName("belongs_to_collection")
        val belongsToCollection: BelongsToCollectionApiResponse?,
        val genres: List<SimpleValueApiResponse>,
        @SerializedName("production_companies")
        val productionCompanies: List<SimpleValueApiResponse>,
        @SerializedName("production_countries")
        val productionCountries: List<ProductionCountriesApiResponse>,
        @SerializedName("spoken_languages")
        val spokenLanguages: List<SpokenLanguagesApiResponse>) {

    fun toAppDomain(): MovieDetailAppDomain =
            MovieDetailAppDomain(voteCount, id, video, voteAverage, title, popularity, posterPath, originalLanguage, originalTitle, backdropPath, adult, overview, releaseDate, budget, homepage, populatiry, imdbId, revenue, runtime, status, tagline, belongsToCollection?.toAppDomain(), genres.map { it.toAppDomain() }, productionCompanies.map { it.toAppDomain() }, productionCountries.map { it.toAppDomain() }, spokenLanguages.map { it.toAppDomain() })

}