package jp.kirin3.anytimeqiita.di

import dagger.Component
import jp.kirin3.anytimeqiita.ui.sample.view.SampleFragment
import jp.kirin3.anytimeqiita.ui.stocks.StocksFragment
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class]
)
interface AppComponent {
    // インジェクトされるクラスを記載していく
    fun inject(fragment: SampleFragment)
    fun inject(fragment: StocksFragment)
}