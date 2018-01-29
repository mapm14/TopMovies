package movies.manuelperera.com.topmovies.unittests.config.model

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import manuelperera.com.base.client.transaction.Transaction
import manuelperera.com.base.client.transaction.TransactionStatus
import movies.manuelperera.com.topmovies.domain.model.ConfigModel
import movies.manuelperera.com.topmovies.domain.objects.domain.ConfigAppDomain
import movies.manuelperera.com.topmovies.domain.objects.domain.ImagesAppDomain
import movies.manuelperera.com.topmovies.domain.repository.api.ConfigApiRepository
import movies.manuelperera.com.topmovies.domain.repository.cache.ConfigCacheRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ConfigModelUnitTest {

    @Mock
    private lateinit var configApiRepository: ConfigApiRepository

    @Mock
    private lateinit var configCacheRepository: ConfigCacheRepository

    private lateinit var configModel: ConfigModel

    private val configAppDomain = ConfigAppDomain(ImagesAppDomain(" ", listOf()))

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        configModel = ConfigModel(configApiRepository, configCacheRepository)
    }

    @Test
    fun getConfig() {
        whenever(configApiRepository.getConfig()).doReturn(Observable.create { observer ->
            observer.onNext(Transaction(configAppDomain, TransactionStatus.SUCCESS))
            observer.onComplete()
        })

        whenever(configCacheRepository.getConfig()).doReturn(Observable.create { observer ->
            observer.onNext(Transaction(configAppDomain, TransactionStatus.SUCCESS))
            observer.onComplete()
        })

        val testObserver = configModel.getConfig().test()

        testObserver.assertComplete()
        testObserver.assertValueCount(1)
        testObserver.assertValue { transaction ->
            transaction.data == configAppDomain && transaction.isSuccess()
        }
    }
}