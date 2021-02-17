package jp.kirin3.anytimeqiita.ui.sample

import android.content.Context
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import jp.kirin3.anytimeqiita.data.entity.SampleDataResult

class SampleDataStore(
    private val retrofitFactory: RetrofitFactory
) : SampleRepository {

    override fun loadSampleDataResult(
        context: Context,
        code: String
    ): Single<SampleDataResult> {
        val api = SampleAPIClient(retrofitFactory.create())
        return api.sampleValidate(code)
            .subscribeOn(Schedulers.io())
            .doAfterSuccess {
//                it.updateCache(context)
            }
    }
}