package movies.manuelperera.com.topmovies.domain.objects.api

import com.google.gson.annotations.SerializedName
import movies.manuelperera.com.topmovies.domain.objects.domain.ProductionCountriesAppDomain

data class ProductionCountriesApiResponse(@SerializedName("iso_3166_1")
                                          val iso: String,
                                          val name: String) {

    fun toAppDomain(): ProductionCountriesAppDomain =
            ProductionCountriesAppDomain(iso, name)

}