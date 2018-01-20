package movies.manuelperera.com.topmovies.injections.module.client;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import okhttp3.OkHttpClient;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ApiModule_OkHttpClientFactory implements Factory<OkHttpClient> {
  private final ApiModule module;

  public ApiModule_OkHttpClientFactory(ApiModule module) {
    this.module = module;
  }

  @Override
  public OkHttpClient get() {
    return Preconditions.checkNotNull(
        module.okHttpClient(), "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<OkHttpClient> create(ApiModule module) {
    return new ApiModule_OkHttpClientFactory(module);
  }
}
