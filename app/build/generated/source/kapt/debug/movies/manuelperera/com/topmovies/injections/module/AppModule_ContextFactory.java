package movies.manuelperera.com.topmovies.injections.module;

import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AppModule_ContextFactory implements Factory<Context> {
  private final AppModule module;

  public AppModule_ContextFactory(AppModule module) {
    this.module = module;
  }

  @Override
  public Context get() {
    return Preconditions.checkNotNull(
        module.context(), "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<Context> create(AppModule module) {
    return new AppModule_ContextFactory(module);
  }
}
