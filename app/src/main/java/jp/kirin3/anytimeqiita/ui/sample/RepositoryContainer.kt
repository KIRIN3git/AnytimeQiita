package jp.kirin3.anytimeqiita.ui.sample

object RepositoryContainer {
    val sample: SampleRepository = SampleDataStore(RetrofitFactoryImpl())
}