package movies.manuelperera.com.topmovies.injections.module.client

import dagger.Module
import dagger.Provides
import manuelperera.com.base.client.transaction.TransactionRequestFactory
import movies.manuelperera.com.topmovies.client.transaction.TransactionRequestFactoryImpl
import movies.manuelperera.com.topmovies.domain.objects.api.ConfigApiResponse
import movies.manuelperera.com.topmovies.domain.objects.api.TopRatedMoviesApiResponse

@Module
class TransactionModule {

    @Provides
    fun topRatedMoviesApiResponseTransactionRequestFactory(): TransactionRequestFactory<TopRatedMoviesApiResponse> =
            TransactionRequestFactoryImpl()

    @Provides
    fun configApiResponseTransactionRequestFactory(): TransactionRequestFactory<ConfigApiResponse> =
            TransactionRequestFactoryImpl()

}