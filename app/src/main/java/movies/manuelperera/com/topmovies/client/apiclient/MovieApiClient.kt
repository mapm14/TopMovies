package movies.manuelperera.com.topmovies.client.apiclient

import io.reactivex.Observable
import movies.manuelperera.com.topmovies.domain.objects.api.MovieDetailApiResponse
import movies.manuelperera.com.topmovies.domain.objects.api.MoviesListApiResponse
import retrofit2.adapter.rxjava2.Result

interface MovieApiClient {

    fun getTopRatedMovies(page: Int): Observable<Result<MoviesListApiResponse>>

    fun getSimilarMovies(movieId: Int, page: Int): Observable<Result<MoviesListApiResponse>>

    fun getMovieDetail(movieId: Int): Observable<Result<MovieDetailApiResponse>>

}