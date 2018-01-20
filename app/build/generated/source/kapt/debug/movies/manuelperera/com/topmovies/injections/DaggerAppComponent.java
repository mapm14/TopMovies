package movies.manuelperera.com.topmovies.injections;

import android.content.Context;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import movies.manuelperera.com.topmovies.injections.module.AppModule;
import movies.manuelperera.com.topmovies.injections.module.AppModule_ContextFactory;
import movies.manuelperera.com.topmovies.injections.module.client.ApiModule;
import movies.manuelperera.com.topmovies.injections.module.client.TransactionModule;
import movies.manuelperera.com.topmovies.injections.module.domain.model.ModelModule;
import movies.manuelperera.com.topmovies.injections.module.domain.repository.ApiRepositoryModule;
import movies.manuelperera.com.topmovies.injections.module.domain.repository.CacheRepositoryModule;
import movies.manuelperera.com.topmovies.injections.module.domain.service.BusinessServiceModule;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DaggerAppComponent implements AppComponent {
  private Provider<Context> contextProvider;

  private DaggerAppComponent(Builder builder) {
    initialize(builder);
  }

  public static Builder builder() {
    return new Builder();
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Builder builder) {
    this.contextProvider = DoubleCheck.provider(AppModule_ContextFactory.create(builder.appModule));
  }

  @Override
  public Context provideContext() {
    return contextProvider.get();
  }

  public static final class Builder {
    private AppModule appModule;

    private Builder() {}

    public AppComponent build() {
      if (appModule == null) {
        throw new IllegalStateException(AppModule.class.getCanonicalName() + " must be set");
      }
      return new DaggerAppComponent(this);
    }

    public Builder appModule(AppModule appModule) {
      this.appModule = Preconditions.checkNotNull(appModule);
      return this;
    }

    /**
     * @deprecated This module is declared, but an instance is not used in the component. This
     *     method is a no-op. For more, see https://google.github.io/dagger/unused-modules.
     */
    @Deprecated
    public Builder apiModule(ApiModule apiModule) {
      Preconditions.checkNotNull(apiModule);
      return this;
    }

    /**
     * @deprecated This module is declared, but an instance is not used in the component. This
     *     method is a no-op. For more, see https://google.github.io/dagger/unused-modules.
     */
    @Deprecated
    public Builder transactionModule(TransactionModule transactionModule) {
      Preconditions.checkNotNull(transactionModule);
      return this;
    }

    /**
     * @deprecated This module is declared, but an instance is not used in the component. This
     *     method is a no-op. For more, see https://google.github.io/dagger/unused-modules.
     */
    @Deprecated
    public Builder businessServiceModule(BusinessServiceModule businessServiceModule) {
      Preconditions.checkNotNull(businessServiceModule);
      return this;
    }

    /**
     * @deprecated This module is declared, but an instance is not used in the component. This
     *     method is a no-op. For more, see https://google.github.io/dagger/unused-modules.
     */
    @Deprecated
    public Builder modelModule(ModelModule modelModule) {
      Preconditions.checkNotNull(modelModule);
      return this;
    }

    /**
     * @deprecated This module is declared, but an instance is not used in the component. This
     *     method is a no-op. For more, see https://google.github.io/dagger/unused-modules.
     */
    @Deprecated
    public Builder apiRepositoryModule(ApiRepositoryModule apiRepositoryModule) {
      Preconditions.checkNotNull(apiRepositoryModule);
      return this;
    }

    /**
     * @deprecated This module is declared, but an instance is not used in the component. This
     *     method is a no-op. For more, see https://google.github.io/dagger/unused-modules.
     */
    @Deprecated
    public Builder cacheRepositoryModule(CacheRepositoryModule cacheRepositoryModule) {
      Preconditions.checkNotNull(cacheRepositoryModule);
      return this;
    }
  }
}
