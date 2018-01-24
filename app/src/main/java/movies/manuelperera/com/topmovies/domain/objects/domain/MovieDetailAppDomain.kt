package movies.manuelperera.com.topmovies.domain.objects.domain

import movies.manuelperera.com.topmovies.domain.objects.ui.MovieDetailUI

data class MovieDetailAppDomain(
        val voteCount: Int,
        val id: Int,
        val video: Boolean,
        val voteAverage: Double,
        val title: String,
        val popularity: Double,
        val posterPath: String?,
        val originalLanguage: String,
        val originalTitle: String,
        val backdropPath: String?,
        val adult: Boolean,
        val overview: String?,
        val releaseDate: String,
        val budget: Int,
        val homepage: String?,
        val populatiry: Double,
        val imdbId: String?,
        val revenue: Int,
        val runtime: Int?,
        val status: String,
        val tagline: String?,
        val belongsToCollection: BelongsToCollectionAppDomain?,
        val genres: List<SimpleValueAppDomain>,
        val productionCompanies: List<SimpleValueAppDomain>,
        val productionCountries: List<ProductionCountriesAppDomain>,
        val spokenLanguages: List<SpokenLanguagesAppDomain>) {

    fun toAppDomain(): MovieDetailUI =
            MovieDetailUI(voteCount, id, video, voteAverage, title, popularity, posterPath
                    ?: "", originalLanguage, originalTitle, backdropPath ?: "", adult, overview
                    ?: "", releaseDate, budget, homepage, populatiry, imdbId, revenue, runtime, status, tagline, belongsToCollection, genres, productionCompanies, productionCountries, spokenLanguages)

}