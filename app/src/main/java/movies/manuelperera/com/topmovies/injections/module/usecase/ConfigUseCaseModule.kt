package movies.manuelperera.com.topmovies.injections.module.usecase

import dagger.Module
import dagger.Provides
import movies.manuelperera.com.topmovies.domain.service.ConfigService
import movies.manuelperera.com.topmovies.usecase.movie.GetConfigUseCase

@Module
class ConfigUseCaseModule {

    @Provides
    fun getConfigUseCase(configService: ConfigService): GetConfigUseCase =
            GetConfigUseCase(configService)

}