package jp.kirin3.anytimeqiita.usecase

import io.reactivex.Single
import jp.kirin3.anytimeqiita.data.entity.SampleDataResult

interface SampleUseCase {

    fun outLog()

    fun loadSampleDataResult(
        code: String
    ): Single<SampleDataResult>
}