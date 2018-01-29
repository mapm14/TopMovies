package movies.manuelperera.com.topmovies.domain.repository.cache

import io.reactivex.Completable
import io.reactivex.Observable
import manuelperera.com.base.client.transaction.Transaction
import manuelperera.com.base.client.transaction.TransactionStatus
import movies.manuelperera.com.topmovies.domain.objects.domain.ConfigAppDomain

open class ConfigCacheRepository {

    private var config: ConfigAppDomain? = null

    open fun cacheConfig(c: ConfigAppDomain?): Completable =
            Completable.create {
                config = c
                it.onComplete()
            }

    open fun getConfig(): Observable<Transaction<ConfigAppDomain>> =
            Observable.create {
                if (config != null)
                    it.onNext(Transaction(config, TransactionStatus.SUCCESS))
                it.onComplete()
            }

}