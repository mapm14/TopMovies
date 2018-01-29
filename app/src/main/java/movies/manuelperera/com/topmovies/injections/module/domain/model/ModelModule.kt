package movies.manuelperera.com.topmovies.injections.module.domain.model

import dagger.Module
import dagger.Provides
import movies.manuelperera.com.topmovies.domain.model.ConfigModel
import movies.manuelperera.com.topmovies.domain.model.MovieModel
import movies.manuelperera.com.topmovies.domain.repository.api.ConfigApiRepository
import movies.manuelperera.com.topmovies.domain.repository.api.MovieApiRepository
import movies.manuelperera.com.topmovies.domain.repository.cache.ConfigCacheRepository

@Module
class ModelModule {

    @Provides
    fun movieModel(movieApiRepository: MovieApiRepository): MovieModel =
            MovieModel(movieApiRepository)

    @Provides
    fun configModel(configApiRepository: ConfigApiRepository, configCacheRepository: ConfigCacheRepository): ConfigModel =
            ConfigModel(configApiRepository, configCacheRepository)

}