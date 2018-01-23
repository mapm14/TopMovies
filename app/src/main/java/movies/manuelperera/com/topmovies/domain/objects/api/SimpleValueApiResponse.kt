package movies.manuelperera.com.topmovies.domain.objects.api

import movies.manuelperera.com.topmovies.domain.objects.domain.SimpleValueAppDomain

data class SimpleValueApiResponse(val id: Int,
                                  val name: String) {

    fun toAppDomain(): SimpleValueAppDomain =
            SimpleValueAppDomain(id, name)

}