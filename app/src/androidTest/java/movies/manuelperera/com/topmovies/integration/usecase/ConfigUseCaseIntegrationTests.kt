package movies.manuelperera.com.topmovies.integration.usecase

import android.support.test.runner.AndroidJUnit4
import movies.manuelperera.com.topmovies.usecase.movie.GetConfigUseCase
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ConfigUseCaseIntegrationTests : UseCasesIntegrationTests() {

    private val getConfigUseCase: GetConfigUseCase = daggerAppComponent.provideGetConfigUseCase()

    @Test
    fun getConfigUseCase() {
        val testObserver = getConfigUseCase.configService.getConfig().test()

        testObserver
                .assertComplete()
                .assertValueCount(1)
                .assertNoErrors()
                .assertValue { transaction ->
                    transaction.isSuccess() &&
                            transaction.data != null &&
                            transaction.data!!.images.secureBaseUrl == "https://image.tmdb.org/t/p/"
                }
    }

}