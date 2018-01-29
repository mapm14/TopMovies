package movies.manuelperera.com.topmovies.integration.usecase

import android.support.test.runner.AndroidJUnit4
import movies.manuelperera.com.topmovies.domain.objects.ui.MovieUI
import movies.manuelperera.com.topmovies.usecase.movie.GetMovieDetailUseCase
import movies.manuelperera.com.topmovies.usecase.movie.GetSimilarMoviesUseCase
import movies.manuelperera.com.topmovies.usecase.movie.GetTopRatedMoviesUseCase
import movies.manuelperera.com.topmovies.usecase.movie.SetMovieSelectedUseCase
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieUseCaseIntegrationTests : UseCasesIntegrationTests() {

    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase = daggerAppComponent.provideGetTopRatedMoviesUseCase()

    private val setMovieSelectedUseCase: SetMovieSelectedUseCase = daggerAppComponent.provideSetMovieSelectedUseCase()

    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase = daggerAppComponent.provideGetSimilarMoviesUseCase()

    private val getMovieDetailUseCase: GetMovieDetailUseCase = daggerAppComponent.provideGetMovieDetailUseCase()

    @Test
    fun getTopRatedMoviesUseCase() {
        val testObserver = getTopRatedMoviesUseCase.movieService.getTopRatedMovies().test()

        testObserver
                .assertComplete()
                .assertValueCount(1)
                .assertNoErrors()
                .assertValue { transaction ->
                    transaction.isSuccess() &&
                            transaction.data != null &&
                            transaction.data!!.page == 1 &&
                            transaction.data!!.movies[0].id == 19404 &&
                            transaction.data!!.movies[0].title == "Dilwale Dulhania Le Jayenge" &&
                            transaction.data!!.movies[1].id == 20532 &&
                            transaction.data!!.movies[1].title == "Sansho the Bailiff"
                }
    }

    @Test
    fun getSimilarMoviesUseCase() {
        setMovieSelectedUseCase.bind(SetMovieSelectedUseCase.Params(MovieUI(id = 19404))).andThen {
            val testObserver = getSimilarMoviesUseCase.movieService.getSimilarMovies().test()

            testObserver
                    .assertComplete()
                    .assertValueCount(1)
                    .assertNoErrors()
                    .assertValue { transaction ->
                        transaction.isSuccess() &&
                                transaction.data != null &&
                                transaction.data!!.page == 1 &&
                                transaction.data!!.movies[0].id == 19404 &&
                                transaction.data!!.movies[0].title == "Dilwale Dulhania Le Jayenge" &&
                                transaction.data!!.movies[1].id == 812 &&
                                transaction.data!!.movies[1].title == "Aladdin"
                    }
        }
    }

    @Test
    fun getMovieDetailUseCase() {
        val testObserver = getMovieDetailUseCase.movieService.getMovieDetail(812).test()

        testObserver
                .assertComplete()
                .assertValueCount(1)
                .assertNoErrors()
                .assertValue { transaction ->
                    transaction.isSuccess() &&
                            transaction.data != null &&
                            transaction.data!!.id == 812 &&
                            transaction.data!!.title == "Aladdin" &&
                            transaction.data!!.homepage == "http://movies.disney.com/aladdin"
                }
    }

}