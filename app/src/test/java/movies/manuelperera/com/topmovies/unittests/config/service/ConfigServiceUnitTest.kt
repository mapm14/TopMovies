package movies.manuelperera.com.topmovies.unittests.config.service

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import manuelperera.com.base.client.transaction.Transaction
import manuelperera.com.base.client.transaction.TransactionStatus
import movies.manuelperera.com.topmovies.domain.model.ConfigModel
import movies.manuelperera.com.topmovies.domain.objects.domain.ConfigAppDomain
import movies.manuelperera.com.topmovies.domain.objects.domain.ImagesAppDomain
import movies.manuelperera.com.topmovies.domain.service.ConfigService
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ConfigServiceUnitTest {

    @Mock
    private lateinit var configModel: ConfigModel

    private lateinit var configService: ConfigService

    private val configAppDomain = ConfigAppDomain(ImagesAppDomain(" ", listOf()))

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        configService = ConfigService(configModel)
    }

    @Test
    fun getConfig() {
        whenever(configModel.getConfig()).doReturn(Observable.create { observer ->
            observer.onNext(Transaction(configAppDomain, TransactionStatus.SUCCESS))
            observer.onComplete()
        })

        val testObserver = configService.getConfig().test()

        testObserver.assertComplete()
        testObserver.assertValueCount(1)
        testObserver.assertValue { transaction ->
            transaction.data == configAppDomain && transaction.isSuccess()
        }
    }

}