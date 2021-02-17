package jp.kirin3.anytimeqiita.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jp.kirin3.anytimeqiita.MainApplication
import javax.inject.Named
import javax.inject.Singleton

@Module(
    includes = [
        UiModule::class,
        UseCaseModule::class,
        InfraModule::class
    ]
)
class AppModule(
    private val application: MainApplication
) {
    @Provides
    fun provideApplication(): Application = application

    @Provides
    fun provideContext(): Context = application.applicationContext

    @Named("ui")
    @Provides
    fun provideUiScheduler(): Scheduler = AndroidSchedulers.mainThread()

    @Named("io")
    @Provides
    fun provideIoScheduler(): Scheduler = Schedulers.io()

    @Singleton
    @Provides
    fun provideRetrofitFactory(): RetrofitFactory = RetrofitFactoryImpl()

}