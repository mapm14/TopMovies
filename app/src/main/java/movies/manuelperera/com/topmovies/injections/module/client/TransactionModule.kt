package movies.manuelperera.com.topmovies.injections.module.client

import dagger.Module
import dagger.Provides
import manuelperera.com.base.client.transaction.TransactionRequestFactory
import movies.manuelperera.com.topmovies.client.transaction.TransactionRequestFactoryImpl
import movies.manuelperera.com.topmovies.domain.objects.api.ConfigApiResponse
import movies.manuelperera.com.topmovies.domain.objects.api.MovieDetailApiResponse
import movies.manuelperera.com.topmovies.domain.objects.api.MoviesListApiResponse

@Module
class TransactionModule {

    @Provides
    fun topRatedMoviesApiResponseTransactionRequestFactory(): TransactionRequestFactory<MoviesListApiResponse> =
            TransactionRequestFactoryImpl()

    @Provides
    fun configApiResponseTransactionRequestFactory(): TransactionRequestFactory<ConfigApiResponse> =
            TransactionRequestFactoryImpl()

    @Provides
    fun movieDetailApiResponseTransactionRequestFactory(): TransactionRequestFactory<MovieDetailApiResponse> =
            TransactionRequestFactoryImpl()

}