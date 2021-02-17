package jp.kirin3.anytimeqiita.ui.sample

import io.reactivex.Single
import jp.kirin3.anytimeqiita.data.entity.SampleDataResult

interface SampleUseCase {

    fun outLog()

    fun loadSampleDataResult(
        code: String
    ): Single<SampleDataResult>
}