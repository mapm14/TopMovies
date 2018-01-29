package movies.manuelperera.com.topmovies.client.apiclient

import io.reactivex.Observable
import movies.manuelperera.com.topmovies.client.retrofit.RetrofitMovieApiClient
import movies.manuelperera.com.topmovies.domain.objects.api.MovieDetailApiResponse
import movies.manuelperera.com.topmovies.domain.objects.api.MoviesListApiResponse
import retrofit2.adapter.rxjava2.Result

class MovieApiClientImpl(private val retrofitMovieApiClient: RetrofitMovieApiClient) : MovieApiClient {

    override fun getTopRatedMovies(page: Int): Observable<Result<MoviesListApiResponse>> =
            retrofitMovieApiClient.getTopRatedMovies(page)

    override fun getSimilarMovies(movieId: Int, page: Int): Observable<Result<MoviesListApiResponse>> =
            retrofitMovieApiClient.getSimilarMovies(movieId, page)

    override fun getMovieDetail(movieId: Int): Observable<Result<MovieDetailApiResponse>> =
            retrofitMovieApiClient.getMovieDetail(movieId)

}