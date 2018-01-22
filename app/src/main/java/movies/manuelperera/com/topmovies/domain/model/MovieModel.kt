package movies.manuelperera.com.topmovies.domain.model

import io.reactivex.Observable
import manuelperera.com.base.client.transaction.Transaction
import movies.manuelperera.com.topmovies.domain.objects.domain.TopRatedMoviesAppDomain
import movies.manuelperera.com.topmovies.domain.repository.api.MovieApiRepository

open class MovieModel(private val movieApiRepository: MovieApiRepository) {

    open fun getTopRatedMovies(page: Int): Observable<Transaction<TopRatedMoviesAppDomain>> =
            movieApiRepository.getTopRatedMovies(page)

}