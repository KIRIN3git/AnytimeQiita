package jp.kirin3.anytimeqiita.di

import dagger.Module
import dagger.Provides
import jp.kirin3.anytimeqiita.usecase.SampleUseCase
import jp.kirin3.anytimeqiita.usecase.StocksUseCase
import jp.kirin3.anytimeqiita.usecase.impl.SampleUseCaseImpl
import jp.kirin3.anytimeqiita.usecase.impl.StocksUseCaseImpl

@Module
internal class UseCaseModule {
    @Provides
    fun provideSampleUseCase(useCase: SampleUseCaseImpl): SampleUseCase {
        return useCase
    }

    @Provides
    fun provideStockUseCase(useCase: StocksUseCaseImpl): StocksUseCase {
        return useCase
    }
}