package movies.manuelperera.com.topmovies.domain.objects.api

import com.google.gson.annotations.SerializedName
import movies.manuelperera.com.topmovies.domain.objects.domain.ConfigAppDomain
import movies.manuelperera.com.topmovies.domain.objects.domain.TopRatedMoviesAppDomain

data class ConfigApiResponse(val images: ImagesApiResponse,
                             @SerializedName("change_keys")
                             val changeKeys: MutableList<String>) {

    fun toAppDomain(): ConfigAppDomain =
            ConfigAppDomain(images.toAppDomain())

}