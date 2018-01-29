package movies.manuelperera.com.topmovies.domain.objects.api

import com.google.gson.annotations.SerializedName
import movies.manuelperera.com.topmovies.domain.objects.domain.SpokenLanguagesAppDomain

data class SpokenLanguagesApiResponse(@SerializedName("iso_639_1")
                                      val iso: String,
                                      val name: String) {

    fun toAppDomain(): SpokenLanguagesAppDomain =
            SpokenLanguagesAppDomain(iso, name)

}