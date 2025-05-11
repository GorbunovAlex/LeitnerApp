package alexgorbunov.space.leitnerapp.modules;

import android.app.Application;
import android.content.Context;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

import javax.inject.Singleton;
import java.util.concurrent.Executor;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {
    @Provides
    @Singleton
    Context provideApplicationContext(Application application) {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    Executor provideExecutor() {
        return new Executor() {
            @Override
            public void execute(Runnable command) {
                new Thread(command).start();
            }
        };
    }
}
