package movies.manuelperera.com.topmovies.domain.objects.domain

data class MoviesListAppDomain(val page: Int,
                               val movies: MutableList<MovieAppDomain>)