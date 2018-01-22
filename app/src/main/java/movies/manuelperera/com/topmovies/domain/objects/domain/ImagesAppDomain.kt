package movies.manuelperera.com.topmovies.domain.objects.domain

data class ImagesAppDomain(val secureBaseUrl: String,
                           private val posterSizes: List<String>) {

    fun getChromePosterSizeUrl(): String =
            when {
                posterSizes.contains("w342") -> secureBaseUrl + "w342"
                posterSizes.isNotEmpty() -> secureBaseUrl + posterSizes[0]
                else -> ""
            }

    fun getFullPosterSizeUrl(): String =
            when {
                posterSizes.contains("w780") -> secureBaseUrl + "w780"
                posterSizes.isNotEmpty() -> secureBaseUrl + posterSizes[posterSizes.size - 1]
                else -> ""
            }

}