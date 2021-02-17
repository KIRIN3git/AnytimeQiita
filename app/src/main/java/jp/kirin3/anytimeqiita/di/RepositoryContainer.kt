package jp.kirin3.anytimeqiita.di

import jp.kirin3.anytimeqiita.infra.repository.implementation.SampleDataStore
import jp.kirin3.anytimeqiita.infra.repository.SampleRepository

object RepositoryContainer {
    val sample: SampleRepository = SampleDataStore(RetrofitFactoryImpl())
}