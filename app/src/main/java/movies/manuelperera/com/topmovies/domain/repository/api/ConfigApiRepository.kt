package movies.manuelperera.com.topmovies.domain.repository.api

import io.reactivex.Observable
import manuelperera.com.base.client.transaction.Transaction
import manuelperera.com.base.client.transaction.TransactionRequestFactory
import movies.manuelperera.com.topmovies.client.apiclient.ConfigApiClient
import movies.manuelperera.com.topmovies.domain.objects.api.ConfigApiResponse
import movies.manuelperera.com.topmovies.domain.objects.domain.ConfigAppDomain

open class ConfigApiRepository(private val configApiClient: ConfigApiClient,
                               private val configTransactionRequestFactory: TransactionRequestFactory<ConfigApiResponse>) {

    open fun getConfig(): Observable<Transaction<ConfigAppDomain>> =
            configTransactionRequestFactory.createTransactionRequest().modifyObservable(configApiClient.getConfig()).map { transaction ->
                Transaction(transaction.data?.toAppDomain(), transaction.status)
            }

}