package movies.manuelperera.com.topmovies.domain.service

import io.reactivex.Observable
import manuelperera.com.base.client.transaction.Transaction
import movies.manuelperera.com.topmovies.domain.model.ConfigModel
import movies.manuelperera.com.topmovies.domain.objects.domain.ConfigAppDomain

open class ConfigService(private val configModel: ConfigModel) {

    open fun getConfig(): Observable<Transaction<ConfigAppDomain>> =
            configModel.getConfig()

}