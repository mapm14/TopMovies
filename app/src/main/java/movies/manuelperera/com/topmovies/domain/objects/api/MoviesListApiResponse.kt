package movies.manuelperera.com.topmovies.domain.objects.api

import com.google.gson.annotations.SerializedName
import movies.manuelperera.com.topmovies.domain.objects.domain.MoviesListAppDomain

data class MoviesListApiResponse(val page: Int,
                                 @SerializedName("total_results")
                                 val totalResults: Int,
                                 @SerializedName("total_pages")
                                 val totalPages: Int,
                                 @SerializedName("results")
                                 val movies: MutableList<MovieApiResponse>) {

    fun toAppDomain(): MoviesListAppDomain =
            MoviesListAppDomain(page, movies.mapTo(mutableListOf()) { it.toAppDomain() })

}