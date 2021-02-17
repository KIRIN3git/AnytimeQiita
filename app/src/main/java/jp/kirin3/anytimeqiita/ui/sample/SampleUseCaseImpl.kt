package jp.kirin3.anytimeqiita.usecase.impl

import android.content.Context
import io.reactivex.Single
import jp.kirin3.anytimeqiita.data.entity.SampleDataResult
import jp.kirin3.anytimeqiita.ui.sample.SampleRepository
import jp.kirin3.anytimeqiita.ui.sample.SampleUseCase
import kirin3.jp.mljanken.util.LogUtils.LOGI
import kirin3.jp.mljanken.util.SettingsUtils
import javax.inject.Inject

class SampleUseCaseImpl @Inject constructor(
    private val context: Context,
    private val repository: SampleRepository
) : SampleUseCase {

    override fun outLog() {

        LOGI("LOGGGGGGGGGGGG " + SettingsUtils.getQiitaCode(context))
    }

    override fun loadSampleDataResult(code: String):
            Single<SampleDataResult> {
        return repository.loadSampleDataResult(context, code)
    }
}