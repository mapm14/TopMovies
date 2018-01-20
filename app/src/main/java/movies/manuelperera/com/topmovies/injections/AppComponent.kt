package movies.manuelperera.com.topmovies.injections

import android.content.Context
import dagger.Component
import movies.manuelperera.com.topmovies.injections.module.AppModule
import movies.manuelperera.com.topmovies.injections.module.client.ApiModule
import movies.manuelperera.com.topmovies.injections.module.client.TransactionModule
import movies.manuelperera.com.topmovies.injections.module.domain.model.ModelModule
import movies.manuelperera.com.topmovies.injections.module.domain.repository.ApiRepositoryModule
import movies.manuelperera.com.topmovies.injections.module.domain.repository.CacheRepositoryModule
import movies.manuelperera.com.topmovies.injections.module.domain.service.BusinessServiceModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    (AppModule::class),
    (ApiModule::class),
    (TransactionModule::class),
    (BusinessServiceModule::class),
    (ModelModule::class),
    (ApiRepositoryModule::class),
    (CacheRepositoryModule::class)])
//    (ForecastUseCaseModule::class)])
interface AppComponent {

    fun provideContext(): Context

    // Use Case


}