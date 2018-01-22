package movies.manuelperera.com.topmovies.domain.objects.api

import com.google.gson.annotations.SerializedName
import movies.manuelperera.com.topmovies.domain.objects.domain.TopRatedMoviesAppDomain

data class TopRatedMoviesApiResponse(val page: Int,
                                     @SerializedName("total_results")
                                     val totalResults: Int,
                                     @SerializedName("total_pages")
                                     val totalPages: Int,
                                     @SerializedName("results")
                                     val movies: MutableList<MovieApiResponse>) {

    fun toAppDomain(): TopRatedMoviesAppDomain =
            TopRatedMoviesAppDomain(page, movies.mapTo(mutableListOf()) { it.toAppDomain() })

}