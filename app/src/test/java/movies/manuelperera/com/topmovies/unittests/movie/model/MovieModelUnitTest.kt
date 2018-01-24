package movies.manuelperera.com.topmovies.unittests.movie.model

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import manuelperera.com.base.client.transaction.Transaction
import manuelperera.com.base.client.transaction.TransactionStatus
import movies.manuelperera.com.topmovies.domain.model.MovieModel
import movies.manuelperera.com.topmovies.domain.objects.domain.MovieDetailAppDomain
import movies.manuelperera.com.topmovies.domain.objects.domain.MoviesListAppDomain
import movies.manuelperera.com.topmovies.domain.repository.api.MovieApiRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieModelUnitTest {

    @Mock
    private lateinit var movieApiRepository: MovieApiRepository

    private lateinit var movieModel: MovieModel

    private val moviesListAppDomain = MoviesListAppDomain()

    private val movieDetailAppDomain = MovieDetailAppDomain()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        movieModel = MovieModel(movieApiRepository)
    }

    @Test
    fun getTopRatedMovies() {
        whenever(movieApiRepository.getTopRatedMovies(1)).doReturn(Observable.create { observer ->
            observer.onNext(Transaction(moviesListAppDomain, TransactionStatus.SUCCESS))
            observer.onComplete()
        })

        val testObserver = movieModel.getTopRatedMovies(1).test()

        testObserver.assertComplete()
        testObserver.assertValueCount(1)
        testObserver.assertValue { transaction ->
            transaction.data == moviesListAppDomain && transaction.isSuccess()
        }
    }

    @Test
    fun getSimilarMovies() {
        whenever(movieApiRepository.getSimilarMovies(123, 1)).doReturn(Observable.create { observer ->
            observer.onNext(Transaction(moviesListAppDomain, TransactionStatus.SUCCESS))
            observer.onComplete()
        })

        val testObserver = movieModel.getSimilarMovies(123, 1).test()

        testObserver.assertComplete()
        testObserver.assertValueCount(1)
        testObserver.assertValue { transaction ->
            transaction.data == moviesListAppDomain && transaction.isSuccess()
        }
    }

    @Test
    fun getMovieDetail() {
        whenever(movieApiRepository.getMovieDetail(123)).doReturn(Observable.create { observer ->
            observer.onNext(Transaction(movieDetailAppDomain, TransactionStatus.SUCCESS))
            observer.onComplete()
        })

        val testObserver = movieModel.getMovieDetail(123).test()

        testObserver.assertComplete()
        testObserver.assertValueCount(1)
        testObserver.assertValue { transaction ->
            transaction.data == movieDetailAppDomain && transaction.isSuccess()
        }
    }
}