package movies.manuelperera.com.topmovies.unittests.movie.repository

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import manuelperera.com.base.client.transaction.Transaction
import manuelperera.com.base.client.transaction.TransactionRequest
import manuelperera.com.base.client.transaction.TransactionRequestFactory
import manuelperera.com.base.client.transaction.TransactionStatus
import movies.manuelperera.com.topmovies.client.apiclient.MovieApiClient
import movies.manuelperera.com.topmovies.domain.objects.api.BelongsToCollectionApiResponse
import movies.manuelperera.com.topmovies.domain.objects.api.MovieDetailApiResponse
import movies.manuelperera.com.topmovies.domain.objects.api.MoviesListApiResponse
import movies.manuelperera.com.topmovies.domain.repository.api.MovieApiRepository
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
class MovieApiRepositoryUnitTest {

    @Mock
    private lateinit var movieListTransactionRequest: TransactionRequest<MoviesListApiResponse>

    @Mock
    private lateinit var movieListTransactionRequestFactory: TransactionRequestFactory<MoviesListApiResponse>

    @Mock
    private lateinit var movieDetailTransactionRequest: TransactionRequest<MovieDetailApiResponse>

    @Mock
    private lateinit var movieDetailTransactionRequestFactory: TransactionRequestFactory<MovieDetailApiResponse>

    @Mock
    private lateinit var movieApiClient: MovieApiClient

    private lateinit var movieApiRepository: MovieApiRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        whenever(movieListTransactionRequestFactory.createTransactionRequest()).doReturn(movieListTransactionRequest)
        whenever(movieDetailTransactionRequestFactory.createTransactionRequest()).doReturn(movieDetailTransactionRequest)

        movieApiRepository = MovieApiRepository(movieApiClient, movieListTransactionRequestFactory, movieDetailTransactionRequestFactory)
    }

    @Test
    fun getTopRatedMoviesFromApiWithSuccess() {
        val moviesListApiResponse = MoviesListApiResponse(1, 1000, 10, mutableListOf())
        val moviesListAppDomain = moviesListApiResponse.toAppDomain()

        whenever(movieApiClient.getTopRatedMovies(1)).doReturn(Observable.create { observer ->
            observer.onNext(Result.response(Response.success(moviesListApiResponse)))
            observer.onComplete()
        })

        whenever(movieListTransactionRequest.modifyObservable(any())).doReturn(Observable.create { observer ->
            observer.onNext(Transaction(moviesListApiResponse, TransactionStatus.SUCCESS))
            observer.onComplete()
        })

        val testObserver = movieApiRepository.getTopRatedMovies(1).test()

        testObserver.assertComplete()
        testObserver.assertValueCount(1)
        testObserver.assertValue { transaction ->
            transaction.data == moviesListAppDomain && transaction.isSuccess()
        }
    }

    @Test
    fun getTopRatedMoviesFromApiWithError() {
        whenever(movieApiClient.getTopRatedMovies(1)).doReturn(Observable.create { observer ->
            observer.onComplete()
        })

        whenever(movieListTransactionRequest.modifyObservable(any())).doReturn(Observable.create { observer ->
            observer.onNext(Transaction(status = TransactionStatus.ERROR))
            observer.onComplete()
        })

        val testObserver = movieApiRepository.getTopRatedMovies(1).test()

        testObserver.assertComplete()
        testObserver.assertValueCount(1)
        testObserver.assertValue { transaction ->
            transaction.data == null && transaction.status == TransactionStatus.ERROR
        }
    }

    @Test
    fun getTopRatedMoviesFromApiWithTimeout() {
        whenever(movieApiClient.getTopRatedMovies(1)).doReturn(Observable.create { observer ->
            observer.onNext(Result.response(Response.error(404, ResponseBody.create(MediaType.parse(""), ""))))
            observer.onComplete()
        })

        whenever(movieListTransactionRequest.modifyObservable(any())).doReturn(Observable.create { observer ->
            observer.onNext(Transaction(status = TransactionStatus.TIMEOUT))
            observer.onComplete()
        })

        val testObserver = movieApiRepository.getTopRatedMovies(1).test()

        testObserver.assertComplete()
        testObserver.assertValueCount(1)
        testObserver.assertValue { transaction ->
            transaction.data == null && transaction.status == TransactionStatus.TIMEOUT
        }
    }

    @Test
    fun getSimilarMoviesFromApiWithSuccess() {
        val moviesListApiResponse = MoviesListApiResponse(1, 1000, 10, mutableListOf())
        val moviesListAppDomain = moviesListApiResponse.toAppDomain()

        whenever(movieApiClient.getSimilarMovies(123, 1)).doReturn(Observable.create { observer ->
            observer.onNext(Result.response(Response.success(moviesListApiResponse)))
            observer.onComplete()
        })

        whenever(movieListTransactionRequest.modifyObservable(any())).doReturn(Observable.create { observer ->
            observer.onNext(Transaction(moviesListApiResponse, TransactionStatus.SUCCESS))
            observer.onComplete()
        })

        val testObserver = movieApiRepository.getSimilarMovies(123, 1).test()

        testObserver.assertComplete()
        testObserver.assertValueCount(1)
        testObserver.assertValue { transaction ->
            transaction.data == moviesListAppDomain && transaction.isSuccess()
        }
    }

    @Test
    fun getSimilarMoviesFromApiWithError() {
        whenever(movieApiClient.getSimilarMovies(123, 1)).doReturn(Observable.create { observer ->
            observer.onComplete()
        })

        whenever(movieListTransactionRequest.modifyObservable(any())).doReturn(Observable.create { observer ->
            observer.onNext(Transaction(status = TransactionStatus.ERROR))
            observer.onComplete()
        })

        val testObserver = movieApiRepository.getSimilarMovies(123, 1).test()

        testObserver.assertComplete()
        testObserver.assertValueCount(1)
        testObserver.assertValue { transaction ->
            transaction.data == null && transaction.status == TransactionStatus.ERROR
        }
    }

    @Test
    fun getSimilarMoviesFromApiWithTimeout() {
        whenever(movieApiClient.getSimilarMovies(123, 1)).doReturn(Observable.create { observer ->
            observer.onNext(Result.response(Response.error(404, ResponseBody.create(MediaType.parse(""), ""))))
            observer.onComplete()
        })

        whenever(movieListTransactionRequest.modifyObservable(any())).doReturn(Observable.create { observer ->
            observer.onNext(Transaction(status = TransactionStatus.TIMEOUT))
            observer.onComplete()
        })

        val testObserver = movieApiRepository.getSimilarMovies(123, 1).test()

        testObserver.assertComplete()
        testObserver.assertValueCount(1)
        testObserver.assertValue { transaction ->
            transaction.data == null && transaction.status == TransactionStatus.TIMEOUT
        }
    }

    @Test
    fun getMovieDetailFromApiWithSuccess() {
        val movieDetailApiResponse = MovieDetailApiResponse(0, 0, false, 0.0, "", 0.0, "", "", "", "", false, "", "", 0, "", 0.0, "", 0, 0, "", "", BelongsToCollectionApiResponse(0, "", "", ""), listOf(), listOf(), listOf(), listOf())
        val movieDetailAppDomain = movieDetailApiResponse.toAppDomain()

        whenever(movieApiClient.getMovieDetail(123)).doReturn(Observable.create { observer ->
            observer.onNext(Result.response(Response.success(movieDetailApiResponse)))
            observer.onComplete()
        })

        whenever(movieDetailTransactionRequest.modifyObservable(any())).doReturn(Observable.create { observer ->
            observer.onNext(Transaction(movieDetailApiResponse, TransactionStatus.SUCCESS))
            observer.onComplete()
        })

        val testObserver = movieApiRepository.getMovieDetail(123).test()

        testObserver.assertComplete()
        testObserver.assertValueCount(1)
        testObserver.assertValue { transaction ->
            transaction.data == movieDetailAppDomain && transaction.isSuccess()
        }
    }

    @Test
    fun getMovieDetailFromApiWithError() {
        whenever(movieApiClient.getMovieDetail(123)).doReturn(Observable.create { observer ->
            observer.onComplete()
        })

        whenever(movieDetailTransactionRequest.modifyObservable(any())).doReturn(Observable.create { observer ->
            observer.onNext(Transaction(status = TransactionStatus.ERROR))
            observer.onComplete()
        })

        val testObserver = movieApiRepository.getMovieDetail(123).test()

        testObserver.assertComplete()
        testObserver.assertValueCount(1)
        testObserver.assertValue { transaction ->
            transaction.data == null && transaction.status == TransactionStatus.ERROR
        }
    }

    @Test
    fun getMovieDetailFromApiWithTimeout() {
        whenever(movieApiClient.getMovieDetail(123)).doReturn(Observable.create { observer ->
            observer.onNext(Result.response(Response.error(404, ResponseBody.create(MediaType.parse(""), ""))))
            observer.onComplete()
        })

        whenever(movieDetailTransactionRequest.modifyObservable(any())).doReturn(Observable.create { observer ->
            observer.onNext(Transaction(status = TransactionStatus.TIMEOUT))
            observer.onComplete()
        })

        val testObserver = movieApiRepository.getMovieDetail(123).test()

        testObserver.assertComplete()
        testObserver.assertValueCount(1)
        testObserver.assertValue { transaction ->
            transaction.data == null && transaction.status == TransactionStatus.TIMEOUT
        }
    }
}