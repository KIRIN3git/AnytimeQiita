package jp.kirin3.anytimeqiita.ui.sample

import dagger.Module
import dagger.Provides

@Module
internal class UiModule {
    @Provides
    fun provideSamplePresenter(presenter: SamplePresenterImpl): SamplePresenter {
        return presenter
    }
}