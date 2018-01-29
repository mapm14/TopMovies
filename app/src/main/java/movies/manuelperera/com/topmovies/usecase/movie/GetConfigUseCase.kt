package movies.manuelperera.com.topmovies.usecase.movie

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import manuelperera.com.base.client.transaction.Transaction
import manuelperera.com.base.usecase.UseCase
import movies.manuelperera.com.topmovies.domain.objects.domain.ConfigAppDomain
import movies.manuelperera.com.topmovies.domain.service.ConfigService

open class GetConfigUseCase(private val configService: ConfigService) : UseCase<Observable<Transaction<ConfigAppDomain>>> {

    override fun bind(): Observable<Transaction<ConfigAppDomain>> =
            configService.getConfig().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    open fun testObserver(): TestObserver<Transaction<ConfigAppDomain>> = configService.getConfig().test()

}