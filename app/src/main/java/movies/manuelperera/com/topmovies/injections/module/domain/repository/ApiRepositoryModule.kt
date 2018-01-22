package movies.manuelperera.com.topmovies.injections.module.domain.repository

import dagger.Module
import dagger.Provides
import manuelperera.com.base.client.transaction.TransactionRequestFactory
import movies.manuelperera.com.topmovies.client.apiclient.ConfigApiClient
import movies.manuelperera.com.topmovies.client.apiclient.MovieApiClient
import movies.manuelperera.com.topmovies.domain.objects.api.ConfigApiResponse
import movies.manuelperera.com.topmovies.domain.objects.api.TopRatedMoviesApiResponse
import movies.manuelperera.com.topmovies.domain.repository.api.ConfigApiRepository
import movies.manuelperera.com.topmovies.domain.repository.api.MovieApiRepository

@Module
class ApiRepositoryModule {

    @Provides
    fun movieApiRepository(movieApiClient: MovieApiClient,
                           topRatedMovieApiResponseTransactionRequestFactory: TransactionRequestFactory<TopRatedMoviesApiResponse>): MovieApiRepository =
            MovieApiRepository(movieApiClient, topRatedMovieApiResponseTransactionRequestFactory)

    @Provides
    fun configApiRepository(configApiClient: ConfigApiClient,
                            configApiResponseTransactionRequestFactory: TransactionRequestFactory<ConfigApiResponse>): ConfigApiRepository =
            ConfigApiRepository(configApiClient, configApiResponseTransactionRequestFactory)

}