package movies.manuelperera.com.topmovies.client.apiclient

import io.reactivex.Observable
import movies.manuelperera.com.topmovies.client.retrofit.RetrofitConfigApiClient
import movies.manuelperera.com.topmovies.domain.objects.api.ConfigApiResponse
import retrofit2.adapter.rxjava2.Result

class ConfigApiClientImpl(private val retrofitConfigApiClient: RetrofitConfigApiClient) : ConfigApiClient {

    override fun getConfig(): Observable<Result<ConfigApiResponse>> =
            retrofitConfigApiClient.getConfig()
}