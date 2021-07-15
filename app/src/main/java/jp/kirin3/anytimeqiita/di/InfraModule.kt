package jp.kirin3.anytimeqiita.di

import dagger.Module
import dagger.Provides
import jp.kirin3.anytimeqiita.infra.repository.SampleRepository
import jp.kirin3.anytimeqiita.ui.stocks.StocksRepository
import javax.inject.Singleton

@Module
class InfraModule {
    @Singleton
    @Provides
    fun provideSampleRepository(): SampleRepository {
        return RepositoryContainer.sample
    }

    @Singleton
    @Provides
    fun provideStocksRepository(): StocksRepository {
        return RepositoryContainer.stocks
    }
}