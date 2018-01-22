package movies.manuelperera.com.topmovies.client.retrofit

import io.reactivex.Observable
import movies.manuelperera.com.topmovies.domain.objects.api.ConfigApiResponse
import movies.manuelperera.com.topmovies.domain.objects.api.TopRatedMoviesApiResponse
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitConfigApiClient {

    @GET("configuration")
    fun getConfig(): Observable<Result<ConfigApiResponse>>

}