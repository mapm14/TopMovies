package movies.manuelperera.com.topmovies.domain.objects.domain

data class ImagesAppDomain(val secureBaseUrl: String,
                           private val posterSizes: List<String>) {

    fun getChromePosterSizeUrl(): String =
            when {
                posterSizes.contains("w500") -> secureBaseUrl + "w500"
                posterSizes.isNotEmpty() -> secureBaseUrl + posterSizes[0]
                else -> ""
            }

    fun getFullPosterSizeUrl(): String =
            when {
                posterSizes.contains("w500") -> secureBaseUrl + "w500"
                posterSizes.isNotEmpty() && posterSizes.size > 1 -> secureBaseUrl + posterSizes[posterSizes.size - 1]
                posterSizes.isNotEmpty() -> secureBaseUrl + posterSizes[0]
                else -> ""
            }

}