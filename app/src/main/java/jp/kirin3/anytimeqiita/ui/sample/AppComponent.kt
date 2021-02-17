package jp.kirin3.anytimeqiita.ui.sample

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class]
)
interface AppComponent {
    // インジェクトされるクラスを記載していく
    fun inject(fragment: SampleFragment)
}