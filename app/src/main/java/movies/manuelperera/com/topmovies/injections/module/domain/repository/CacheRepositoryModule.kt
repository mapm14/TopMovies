package movies.manuelperera.com.topmovies.injections.module.domain.repository

import dagger.Module
import dagger.Provides
import movies.manuelperera.com.topmovies.domain.repository.cache.ConfigCacheRepository
import javax.inject.Singleton

@Module
class CacheRepositoryModule {

    @Provides
    @Singleton
    fun configCacheRepository(): ConfigCacheRepository =
            ConfigCacheRepository()

}