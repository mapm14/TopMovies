package movies.manuelperera.com.topmovies.domain.objects.ui

import movies.manuelperera.com.topmovies.domain.objects.domain.BelongsToCollectionAppDomain
import movies.manuelperera.com.topmovies.domain.objects.domain.ProductionCountriesAppDomain
import movies.manuelperera.com.topmovies.domain.objects.domain.SimpleValueAppDomain
import movies.manuelperera.com.topmovies.domain.objects.domain.SpokenLanguagesAppDomain

data class MovieDetailUI(
        val voteCount: Int,
        val id: Int,
        val video: Boolean,
        val voteAverage: Double,
        val title: String,
        val popularity: Double,
        val posterPath: String,
        val originalLanguage: String,
        val originalTitle: String,
        val backdropPath: String,
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
        val spokenLanguages: List<SpokenLanguagesAppDomain>)