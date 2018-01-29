package movies.manuelperera.com.topmovies.unittests.movie.service

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import manuelperera.com.base.client.transaction.Transaction
import manuelperera.com.base.client.transaction.TransactionStatus
import movies.manuelperera.com.topmovies.domain.model.MovieModel
import movies.manuelperera.com.topmovies.domain.objects.domain.MovieAppDomain
import movies.manuelperera.com.topmovies.domain.objects.domain.MovieDetailAppDomain
import movies.manuelperera.com.topmovies.domain.objects.domain.MoviesListAppDomain
import movies.manuelperera.com.topmovies.domain.service.MovieService
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieServiceUnitTest {

    @Mock
    private lateinit var movieModel: MovieModel

    private lateinit var movieService: MovieService

    private val moviesListAppDomain = MoviesListAppDomain()

    private val movieAppDomain = MovieAppDomain()

    private val movieDetailAppDomain = MovieDetailAppDomain()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        movieService = MovieService(movieModel)
    }

    @Test
    fun getTopRatedMovies() {
        movieService.setTopRatedMoviesPagination(1).doOnNext {
            val testObserver = movieService.getTopRatedMovies().test()

            testObserver.assertComplete()
            testObserver.assertValueCount(1)
            testObserver.assertValue { transaction ->
                transaction.data == moviesListAppDomain && transaction.isSuccess()
            }
        }
    }

    @Test
    fun getSimilarMovies() {
        movieService.setMovie(movieAppDomain).andThen {
            movieService.setSimilarMoviesPagination(1).doOnNext {
                val testObserver = movieService.getSimilarMovies().test()

                testObserver.assertComplete()
                testObserver.assertValueCount(1)
                testObserver.assertValue { transaction ->
                    transaction.data == moviesListAppDomain && transaction.isSuccess()
                }
            }
        }
    }

    @Test
    fun getMovieDetail() {
        whenever(movieModel.getMovieDetail(123)).doReturn(Observable.create { observer ->
            observer.onNext(Transaction(movieDetailAppDomain, TransactionStatus.SUCCESS))
            observer.onComplete()
        })

        val testObserver = movieService.getMovieDetail(123).test()

        testObserver.assertComplete()
        testObserver.assertValueCount(1)
        testObserver.assertValue { transaction ->
            transaction.data == movieDetailAppDomain && transaction.isSuccess()
        }
    }

}