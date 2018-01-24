package movies.manuelperera.com.topmovies.domain.objects.domain

data class MoviesListAppDomain(val page: Int = 1,
                               val movies: MutableList<MovieAppDomain> = mutableListOf())