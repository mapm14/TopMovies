package movies.manuelperera.com.topmovies.injections.module.domain.service

import dagger.Module
import dagger.Provides
import movies.manuelperera.com.topmovies.domain.model.ConfigModel
import movies.manuelperera.com.topmovies.domain.model.MovieModel
import movies.manuelperera.com.topmovies.domain.service.ConfigService
import movies.manuelperera.com.topmovies.domain.service.MovieService
import javax.inject.Singleton

@Module
class BusinessServiceModule {

    @Provides
    @Singleton
    fun movieService(movieModel: MovieModel): MovieService =
            MovieService(movieModel)

    @Provides
    @Singleton
    fun configService(configModel: ConfigModel): ConfigService =
            ConfigService(configModel)

}