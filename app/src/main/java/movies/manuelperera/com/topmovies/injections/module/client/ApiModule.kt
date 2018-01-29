package movies.manuelperera.com.topmovies.injections.module.client

import dagger.Module
import dagger.Provides
import movies.manuelperera.com.topmovies.BuildConfig
import movies.manuelperera.com.topmovies.client.apiclient.ConfigApiClient
import movies.manuelperera.com.topmovies.client.apiclient.ConfigApiClientImpl
import movies.manuelperera.com.topmovies.client.apiclient.MovieApiClient
import movies.manuelperera.com.topmovies.client.apiclient.MovieApiClientImpl
import movies.manuelperera.com.topmovies.client.retrofit.RetrofitConfigApiClient
import movies.manuelperera.com.topmovies.client.retrofit.RetrofitMovieApiClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun okHttpClient(): OkHttpClient =
            OkHttpClient().newBuilder()
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
                    })
                    .addInterceptor({ chain ->
                        var request = chain.request()
                        val httpBuilder = request.url().newBuilder()
                                .addQueryParameter("api_key", BuildConfig.MOVIE_API_KEY)
                                .addQueryParameter("language", "en-US")
                        val url = httpBuilder.build()
                        request = request.newBuilder().url(url).build()
                        chain.proceed(request)
                    })
                    .build()

    @Provides
    fun movieApiClient(client: OkHttpClient): MovieApiClient =
            MovieApiClientImpl(Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/3/")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
                    .create(RetrofitMovieApiClient::class.java))

    @Provides
    fun configApiClient(client: OkHttpClient): ConfigApiClient =
            ConfigApiClientImpl(Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/3/")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
                    .create(RetrofitConfigApiClient::class.java))

}