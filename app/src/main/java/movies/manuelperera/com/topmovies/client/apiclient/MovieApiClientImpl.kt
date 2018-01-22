package movies.manuelperera.com.topmovies.client.apiclient

import io.reactivex.Observable
import movies.manuelperera.com.topmovies.client.retrofit.RetrofitMovieApiClient
import movies.manuelperera.com.topmovies.domain.objects.api.TopRatedMoviesApiResponse
import retrofit2.adapter.rxjava2.Result

class MovieApiClientImpl(private val retrofitMovieApiClient: RetrofitMovieApiClient) : MovieApiClient {

    override fun getTopRatedMovies(page: Int): Observable<Result<TopRatedMoviesApiResponse>> =
            retrofitMovieApiClient.getTopRatedMovies(page)

}