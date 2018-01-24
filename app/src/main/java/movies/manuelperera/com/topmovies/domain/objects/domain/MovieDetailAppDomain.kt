package movies.manuelperera.com.topmovies.domain.objects.domain

import movies.manuelperera.com.topmovies.domain.objects.ui.MovieDetailUI

data class MovieDetailAppDomain(
        val voteCount: Int = 0,
        val id: Int = 0,
        val video: Boolean = false,
        val voteAverage: Double = 0.0,
        val title: String = "",
        val popularity: Double = 0.0,
        val posterPath: String? = "",
        val originalLanguage: String = "",
        val originalTitle: String = "",
        val backdropPath: String? = "",
        val adult: Boolean = false,
        val overview: String? = "",
        val releaseDate: String = "",
        val budget: Int = 0,
        val homepage: String? = "",
        val populatiry: Double = 0.0,
        val imdbId: String? = "",
        val revenue: Int = 0,
        val runtime: Int? = 0,
        val status: String = "",
        val tagline: String? = "",
        val belongsToCollection: BelongsToCollectionAppDomain? = BelongsToCollectionAppDomain(0 , "", "", ""),
        val genres: List<SimpleValueAppDomain> = listOf(),
        val productionCompanies: List<SimpleValueAppDomain> = listOf(),
        val productionCountries: List<ProductionCountriesAppDomain> = listOf(),
        val spokenLanguages: List<SpokenLanguagesAppDomain> = listOf()) {

    fun toAppDomain(): MovieDetailUI =
            MovieDetailUI(voteCount, id, video, voteAverage, title, popularity, posterPath
                    ?: "", originalLanguage, originalTitle, backdropPath ?: "", adult, overview
                    ?: "", releaseDate, budget, homepage, populatiry, imdbId, revenue, runtime, status, tagline, belongsToCollection, genres, productionCompanies, productionCountries, spokenLanguages)

}