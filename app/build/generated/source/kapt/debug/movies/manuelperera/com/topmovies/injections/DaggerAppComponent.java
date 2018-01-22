package movies.manuelperera.com.topmovies.injections;

import android.content.Context;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import manuelperera.com.base.client.transaction.TransactionRequestFactory;
import movies.manuelperera.com.topmovies.client.apiclient.ConfigApiClient;
import movies.manuelperera.com.topmovies.client.apiclient.MovieApiClient;
import movies.manuelperera.com.topmovies.domain.model.ConfigModel;
import movies.manuelperera.com.topmovies.domain.model.MovieModel;
import movies.manuelperera.com.topmovies.domain.objects.api.ConfigApiResponse;
import movies.manuelperera.com.topmovies.domain.objects.api.TopRatedMoviesApiResponse;
import movies.manuelperera.com.topmovies.domain.repository.api.ConfigApiRepository;
import movies.manuelperera.com.topmovies.domain.repository.api.MovieApiRepository;
import movies.manuelperera.com.topmovies.domain.repository.cache.ConfigCacheRepository;
import movies.manuelperera.com.topmovies.domain.service.ConfigService;
import movies.manuelperera.com.topmovies.domain.service.MovieService;
import movies.manuelperera.com.topmovies.injections.module.AppModule;
import movies.manuelperera.com.topmovies.injections.module.AppModule_ContextFactory;
import movies.manuelperera.com.topmovies.injections.module.client.ApiModule;
import movies.manuelperera.com.topmovies.injections.module.client.ApiModule_ConfigApiClientFactory;
import movies.manuelperera.com.topmovies.injections.module.client.ApiModule_MovieApiClientFactory;
import movies.manuelperera.com.topmovies.injections.module.client.ApiModule_OkHttpClientFactory;
import movies.manuelperera.com.topmovies.injections.module.client.TransactionModule;
import movies.manuelperera.com.topmovies.injections.module.client.TransactionModule_ConfigApiResponseTransactionRequestFactoryFactory;
import movies.manuelperera.com.topmovies.injections.module.client.TransactionModule_TopRatedMoviesApiResponseTransactionRequestFactoryFactory;
import movies.manuelperera.com.topmovies.injections.module.domain.model.ModelModule;
import movies.manuelperera.com.topmovies.injections.module.domain.model.ModelModule_ConfigModelFactory;
import movies.manuelperera.com.topmovies.injections.module.domain.model.ModelModule_MovieModelFactory;
import movies.manuelperera.com.topmovies.injections.module.domain.repository.ApiRepositoryModule;
import movies.manuelperera.com.topmovies.injections.module.domain.repository.ApiRepositoryModule_ConfigApiRepositoryFactory;
import movies.manuelperera.com.topmovies.injections.module.domain.repository.ApiRepositoryModule_MovieApiRepositoryFactory;
import movies.manuelperera.com.topmovies.injections.module.domain.repository.CacheRepositoryModule;
import movies.manuelperera.com.topmovies.injections.module.domain.repository.CacheRepositoryModule_ConfigCacheRepositoryFactory;
import movies.manuelperera.com.topmovies.injections.module.domain.service.BusinessServiceModule;
import movies.manuelperera.com.topmovies.injections.module.domain.service.BusinessServiceModule_ConfigServiceFactory;
import movies.manuelperera.com.topmovies.injections.module.domain.service.BusinessServiceModule_MovieServiceFactory;
import movies.manuelperera.com.topmovies.injections.module.usecase.ConfigUseCaseModule;
import movies.manuelperera.com.topmovies.injections.module.usecase.MoviesUseCaseModule;
import movies.manuelperera.com.topmovies.usecase.movie.GetConfigUseCase;
import movies.manuelperera.com.topmovies.usecase.movie.GetMovieSelectedUseCase;
import movies.manuelperera.com.topmovies.usecase.movie.GetTopRatedMoviesUseCase;
import movies.manuelperera.com.topmovies.usecase.movie.SetMovieSelectedUseCase;
import movies.manuelperera.com.topmovies.usecase.movie.SetTopRatedMoviesPaginationUseCase;
import okhttp3.OkHttpClient;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DaggerAppComponent implements AppComponent {
  private Provider<Context> contextProvider;

  private Provider<OkHttpClient> okHttpClientProvider;

  private Provider<MovieApiClient> movieApiClientProvider;

  private Provider<TransactionRequestFactory<TopRatedMoviesApiResponse>>
      topRatedMoviesApiResponseTransactionRequestFactoryProvider;

  private Provider<MovieApiRepository> movieApiRepositoryProvider;

  private Provider<MovieModel> movieModelProvider;

  private Provider<MovieService> movieServiceProvider;

  private MoviesUseCaseModule moviesUseCaseModule;

  private Provider<ConfigApiClient> configApiClientProvider;

  private Provider<TransactionRequestFactory<ConfigApiResponse>>
      configApiResponseTransactionRequestFactoryProvider;

  private Provider<ConfigApiRepository> configApiRepositoryProvider;

  private Provider<ConfigCacheRepository> configCacheRepositoryProvider;

  private Provider<ConfigModel> configModelProvider;

  private Provider<ConfigService> configServiceProvider;

  private ConfigUseCaseModule configUseCaseModule;

  private DaggerAppComponent(Builder builder) {
    initialize(builder);
  }

  public static Builder builder() {
    return new Builder();
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Builder builder) {
    this.contextProvider = DoubleCheck.provider(AppModule_ContextFactory.create(builder.appModule));
    this.okHttpClientProvider =
        DoubleCheck.provider(ApiModule_OkHttpClientFactory.create(builder.apiModule));
    this.movieApiClientProvider =
        ApiModule_MovieApiClientFactory.create(builder.apiModule, okHttpClientProvider);
    this.topRatedMoviesApiResponseTransactionRequestFactoryProvider =
        TransactionModule_TopRatedMoviesApiResponseTransactionRequestFactoryFactory.create(
            builder.transactionModule);
    this.movieApiRepositoryProvider =
        ApiRepositoryModule_MovieApiRepositoryFactory.create(
            builder.apiRepositoryModule,
            movieApiClientProvider,
            topRatedMoviesApiResponseTransactionRequestFactoryProvider);
    this.movieModelProvider =
        ModelModule_MovieModelFactory.create(builder.modelModule, movieApiRepositoryProvider);
    this.movieServiceProvider =
        DoubleCheck.provider(
            BusinessServiceModule_MovieServiceFactory.create(
                builder.businessServiceModule, movieModelProvider));
    this.moviesUseCaseModule = builder.moviesUseCaseModule;
    this.configApiClientProvider =
        ApiModule_ConfigApiClientFactory.create(builder.apiModule, okHttpClientProvider);
    this.configApiResponseTransactionRequestFactoryProvider =
        TransactionModule_ConfigApiResponseTransactionRequestFactoryFactory.create(
            builder.transactionModule);
    this.configApiRepositoryProvider =
        ApiRepositoryModule_ConfigApiRepositoryFactory.create(
            builder.apiRepositoryModule,
            configApiClientProvider,
            configApiResponseTransactionRequestFactoryProvider);
    this.configCacheRepositoryProvider =
        DoubleCheck.provider(
            CacheRepositoryModule_ConfigCacheRepositoryFactory.create(
                builder.cacheRepositoryModule));
    this.configModelProvider =
        ModelModule_ConfigModelFactory.create(
            builder.modelModule, configApiRepositoryProvider, configCacheRepositoryProvider);
    this.configServiceProvider =
        DoubleCheck.provider(
            BusinessServiceModule_ConfigServiceFactory.create(
                builder.businessServiceModule, configModelProvider));
    this.configUseCaseModule = builder.configUseCaseModule;
  }

  @Override
  public Context provideContext() {
    return contextProvider.get();
  }

  @Override
  public GetTopRatedMoviesUseCase provideGetTopRatedMoviesUseCase() {
    return Preconditions.checkNotNull(
        moviesUseCaseModule.getTopRatedMoviesUseCase(movieServiceProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  @Override
  public SetMovieSelectedUseCase provideSetMovieIdUseCase() {
    return Preconditions.checkNotNull(
        moviesUseCaseModule.setMovieIdUseCase(movieServiceProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  @Override
  public SetTopRatedMoviesPaginationUseCase provideSetTopRatedMoviesPaginationUseCase() {
    return Preconditions.checkNotNull(
        moviesUseCaseModule.setTopRatedMoviesPaginationUseCase(movieServiceProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  @Override
  public GetMovieSelectedUseCase provideGetMovieSelectedUseCase() {
    return Preconditions.checkNotNull(
        moviesUseCaseModule.getMovieSelectedUseCase(movieServiceProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  @Override
  public GetConfigUseCase provideGetConfigUseCase() {
    return Preconditions.checkNotNull(
        configUseCaseModule.getConfigUseCase(configServiceProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static final class Builder {
    private AppModule appModule;

    private ApiModule apiModule;

    private TransactionModule transactionModule;

    private ApiRepositoryModule apiRepositoryModule;

    private ModelModule modelModule;

    private BusinessServiceModule businessServiceModule;

    private MoviesUseCaseModule moviesUseCaseModule;

    private CacheRepositoryModule cacheRepositoryModule;

    private ConfigUseCaseModule configUseCaseModule;

    private Builder() {}

    public AppComponent build() {
      if (appModule == null) {
        throw new IllegalStateException(AppModule.class.getCanonicalName() + " must be set");
      }
      if (apiModule == null) {
        this.apiModule = new ApiModule();
      }
      if (transactionModule == null) {
        this.transactionModule = new TransactionModule();
      }
      if (apiRepositoryModule == null) {
        this.apiRepositoryModule = new ApiRepositoryModule();
      }
      if (modelModule == null) {
        this.modelModule = new ModelModule();
      }
      if (businessServiceModule == null) {
        this.businessServiceModule = new BusinessServiceModule();
      }
      if (moviesUseCaseModule == null) {
        this.moviesUseCaseModule = new MoviesUseCaseModule();
      }
      if (cacheRepositoryModule == null) {
        this.cacheRepositoryModule = new CacheRepositoryModule();
      }
      if (configUseCaseModule == null) {
        this.configUseCaseModule = new ConfigUseCaseModule();
      }
      return new DaggerAppComponent(this);
    }

    public Builder appModule(AppModule appModule) {
      this.appModule = Preconditions.checkNotNull(appModule);
      return this;
    }

    public Builder apiModule(ApiModule apiModule) {
      this.apiModule = Preconditions.checkNotNull(apiModule);
      return this;
    }

    public Builder transactionModule(TransactionModule transactionModule) {
      this.transactionModule = Preconditions.checkNotNull(transactionModule);
      return this;
    }

    public Builder businessServiceModule(BusinessServiceModule businessServiceModule) {
      this.businessServiceModule = Preconditions.checkNotNull(businessServiceModule);
      return this;
    }

    public Builder modelModule(ModelModule modelModule) {
      this.modelModule = Preconditions.checkNotNull(modelModule);
      return this;
    }

    public Builder apiRepositoryModule(ApiRepositoryModule apiRepositoryModule) {
      this.apiRepositoryModule = Preconditions.checkNotNull(apiRepositoryModule);
      return this;
    }

    public Builder cacheRepositoryModule(CacheRepositoryModule cacheRepositoryModule) {
      this.cacheRepositoryModule = Preconditions.checkNotNull(cacheRepositoryModule);
      return this;
    }

    public Builder moviesUseCaseModule(MoviesUseCaseModule moviesUseCaseModule) {
      this.moviesUseCaseModule = Preconditions.checkNotNull(moviesUseCaseModule);
      return this;
    }

    public Builder configUseCaseModule(ConfigUseCaseModule configUseCaseModule) {
      this.configUseCaseModule = Preconditions.checkNotNull(configUseCaseModule);
      return this;
    }
  }
}
