package movies.manuelperera.com.topmovies.client.retrofit

import io.reactivex.Observable
import movies.manuelperera.com.topmovies.domain.objects.api.TopRatedMoviesApiResponse
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitMovieApiClient {

    @GET("movie/top_rated")
    fun getTopRatedMovies(@Query("page") page: Int): Observable<Result<TopRatedMoviesApiResponse>>

}