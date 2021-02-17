package jp.kirin3.anytimeqiita.ui.sample

import dagger.Module
import dagger.Provides
import jp.kirin3.anytimeqiita.usecase.impl.SampleUseCaseImpl

@Module
internal class UseCaseModule {
    @Provides
    fun provideSampleUseCase(useCase: SampleUseCaseImpl): SampleUseCase {
        return useCase
    }
}