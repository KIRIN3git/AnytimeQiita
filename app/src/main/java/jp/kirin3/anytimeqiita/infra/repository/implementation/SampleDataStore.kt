package jp.kirin3.anytimeqiita.infra.repository.implementation

import android.content.Context
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import jp.kirin3.anytimeqiita.data.entity.SampleDataResult
import jp.kirin3.anytimeqiita.di.RetrofitFactory
import jp.kirin3.anytimeqiita.infra.repository.SampleRepository
import jp.kirin3.anytimeqiita.infra.network.apiclient.SampleAPIClient

class SampleDataStore(
    private val retrofitFactory: RetrofitFactory
) : SampleRepository {

    override fun loadSampleDataResult(
        context: Context,
        code: String
    ): Single<SampleDataResult> {
        val api = SampleAPIClient(retrofitFactory.buildRetrofit())
        return api.sampleValidate(code)
            .subscribeOn(Schedulers.io())
            .doAfterSuccess {
//                it.updateCache(context)
            }
    }
}