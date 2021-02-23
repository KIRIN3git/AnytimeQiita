package jp.kirin3.anytimeqiita.di

import dagger.Module
import dagger.Provides
import jp.kirin3.anytimeqiita.usecase.SampleUseCase
import jp.kirin3.anytimeqiita.usecase.impl.SampleUseCaseImpl

@Module
internal class UseCaseModule {
    @Provides
    fun provideSampleUseCase(useCase: SampleUseCaseImpl): SampleUseCase {
        return useCase
    }
}