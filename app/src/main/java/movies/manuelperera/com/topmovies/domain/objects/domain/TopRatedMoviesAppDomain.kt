package movies.manuelperera.com.topmovies.domain.objects.domain

data class TopRatedMoviesAppDomain(val page: Int,
                                   val movies: MutableList<MovieAppDomain>)