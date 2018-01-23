package movies.manuelperera.com.topmovies.client.retrofit

import io.reactivex.Observable
import movies.manuelperera.com.topmovies.domain.objects.api.MovieDetailApiResponse
import movies.manuelperera.com.topmovies.domain.objects.api.MoviesListApiResponse
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitMovieApiClient {

    @GET("movie/top_rated")
    fun getTopRatedMovies(@Query("page") page: Int): Observable<Result<MoviesListApiResponse>>

    @GET("movie/{movie_id}/similar")
    fun getSimilarMovies(@Path("movie_id") movieId: Int, @Query("page") page: Int): Observable<Result<MoviesListApiResponse>>

    @GET("movie/{movie_id}")
    fun getMovieDetail(@Path("movie_id") movieId: Int): Observable<Result<MovieDetailApiResponse>>

}