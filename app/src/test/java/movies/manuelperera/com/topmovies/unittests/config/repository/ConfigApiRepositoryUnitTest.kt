package movies.manuelperera.com.topmovies.unittests.config.repository

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import manuelperera.com.base.client.transaction.Transaction
import manuelperera.com.base.client.transaction.TransactionRequest
import manuelperera.com.base.client.transaction.TransactionRequestFactory
import manuelperera.com.base.client.transaction.TransactionStatus
import movies.manuelperera.com.topmovies.client.apiclient.ConfigApiClient
import movies.manuelperera.com.topmovies.domain.objects.api.ConfigApiResponse
import movies.manuelperera.com.topmovies.domain.objects.api.ImagesApiResponse
import movies.manuelperera.com.topmovies.domain.repository.api.ConfigApiRepository
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import retrofit2.adapter.rxjava2.Result

@RunWith(MockitoJUnitRunner::class)
class ConfigApiRepositoryUnitTest {

    @Mock
    private lateinit var transactionRequest: TransactionRequest<ConfigApiResponse>

    @Mock
    private lateinit var transactionRequestFactory: TransactionRequestFactory<ConfigApiResponse>

    @Mock
    private lateinit var configApiClient: ConfigApiClient

    private lateinit var configApiRepository: ConfigApiRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        whenever(transactionRequestFactory.createTransactionRequest()).doReturn(transactionRequest)

        configApiRepository = ConfigApiRepository(configApiClient, transactionRequestFactory)
    }

    @Test
    fun getConfigFromApiWithSuccess() {
        val configApiResponse = ConfigApiResponse(ImagesApiResponse(" ", " ", listOf(), listOf(), listOf(), listOf(), listOf()), mutableListOf())
        val configAppDomain = configApiResponse.toAppDomain()

        whenever(configApiClient.getConfig()).doReturn(Observable.create { observer ->
            observer.onNext(Result.response(Response.success(configApiResponse)))
            observer.onComplete()
        })

        whenever(transactionRequest.modifyObservable(any())).doReturn(Observable.create { observer ->
            observer.onNext(Transaction(configApiResponse, TransactionStatus.SUCCESS))
            observer.onComplete()
        })

        val testObserver = configApiRepository.getConfig().test()

        testObserver.assertComplete()
        testObserver.assertValueCount(1)
        testObserver.assertValue { transaction ->
            transaction.data == configAppDomain && transaction.isSuccess()
        }
    }

    @Test
    fun gesConfigFromApiWithError() {
        whenever(configApiClient.getConfig()).doReturn(Observable.create { observer ->
            observer.onComplete()
        })

        whenever(transactionRequest.modifyObservable(any())).doReturn(Observable.create { observer ->
            observer.onNext(Transaction(status = TransactionStatus.ERROR))
            observer.onComplete()
        })

        val testObserver = configApiRepository.getConfig().test()

        testObserver.assertComplete()
        testObserver.assertValueCount(1)
        testObserver.assertValue { transaction ->
            transaction.data == null && transaction.status == TransactionStatus.ERROR
        }
    }

    @Test
    fun getConfigFromApiWithTimeout() {
        whenever(configApiClient.getConfig()).doReturn(Observable.create { observer ->
            observer.onNext(Result.response(Response.error(404, ResponseBody.create(MediaType.parse(""), ""))))
            observer.onComplete()
        })

        whenever(transactionRequest.modifyObservable(any())).doReturn(Observable.create { observer ->
            observer.onNext(Transaction(status = TransactionStatus.TIMEOUT))
            observer.onComplete()
        })

        val testObserver = configApiRepository.getConfig().test()

        testObserver.assertComplete()
        testObserver.assertValueCount(1)
        testObserver.assertValue { transaction ->
            transaction.data == null && transaction.status == TransactionStatus.TIMEOUT
        }
    }
}