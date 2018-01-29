package movies.manuelperera.com.topmovies.client.retrofit

import io.reactivex.Observable
import movies.manuelperera.com.topmovies.domain.objects.api.ConfigApiResponse
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.GET

interface RetrofitConfigApiClient {

    @GET("configuration")
    fun getConfig(): Observable<Result<ConfigApiResponse>>

}