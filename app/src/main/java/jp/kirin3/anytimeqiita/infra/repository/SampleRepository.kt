package jp.kirin3.anytimeqiita.infra.repository


import android.content.Context
import io.reactivex.Single
import jp.kirin3.anytimeqiita.data.entity.SampleDataResult

interface SampleRepository {

    fun loadSampleDataResult(
        context: Context,
        code: String
    ): Single<SampleDataResult>
}