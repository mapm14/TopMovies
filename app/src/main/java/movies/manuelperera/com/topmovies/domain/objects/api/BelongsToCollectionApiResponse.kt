package movies.manuelperera.com.topmovies.domain.objects.api

import com.google.gson.annotations.SerializedName
import movies.manuelperera.com.topmovies.domain.objects.domain.BelongsToCollectionAppDomain

data class BelongsToCollectionApiResponse(val id: Int,
                                          val name: String,
                                          @SerializedName("poster_path")
                                          val posterPath: String?,
                                          @SerializedName("backdrop_path")
                                          val backdropPath: String?) {

    fun toAppDomain(): BelongsToCollectionAppDomain =
            BelongsToCollectionAppDomain(id, name, posterPath, backdropPath)

}