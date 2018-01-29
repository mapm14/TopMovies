package movies.manuelperera.com.topmovies.domain.model

import io.reactivex.Observable
import manuelperera.com.base.client.transaction.Transaction
import manuelperera.com.base.client.transaction.TransactionStatus
import movies.manuelperera.com.topmovies.domain.objects.domain.ConfigAppDomain
import movies.manuelperera.com.topmovies.domain.repository.api.ConfigApiRepository
import movies.manuelperera.com.topmovies.domain.repository.cache.ConfigCacheRepository

open class ConfigModel(private val configApiRepository: ConfigApiRepository,
                       private val configCacheRepository: ConfigCacheRepository) {

    open fun getConfig(): Observable<Transaction<ConfigAppDomain>> =
            configCacheRepository.getConfig()
                    .concatWith(configApiRepository.getConfig()
                            .doOnNext { transaction ->
                                if (transaction.isSuccess())
                                    configCacheRepository.cacheConfig(transaction.data)
                            })
                    .first(Transaction(status = TransactionStatus.EXCEPTION)).toObservable()

}