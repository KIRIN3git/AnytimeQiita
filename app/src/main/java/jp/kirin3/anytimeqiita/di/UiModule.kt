package jp.kirin3.anytimeqiita.di

import dagger.Module
import dagger.Provides
import jp.kirin3.anytimeqiita.ui.sample.presentation.SamplePresenter
import jp.kirin3.anytimeqiita.ui.sample.presentation.SamplePresenterImpl

@Module
internal class UiModule {
    @Provides
    fun provideSamplePresenter(presenter: SamplePresenterImpl): SamplePresenter {
        return presenter
    }
}