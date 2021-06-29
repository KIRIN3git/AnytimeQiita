package jp.kirin3.anytimeqiita.di

import jp.kirin3.anytimeqiita.infra.repository.SampleRepository
import jp.kirin3.anytimeqiita.infra.repository.implementation.SampleDataStore
import jp.kirin3.anytimeqiita.ui.stocks.StocksRepository

object RepositoryContainer {
    val sample: SampleRepository = SampleDataStore(RetrofitFactoryImpl())
    val stocks: StocksRepository = StocksRepository(RetrofitFactoryImpl())
}