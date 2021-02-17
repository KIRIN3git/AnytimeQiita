package jp.kirin3.anytimeqiita.ui.sample.presentation

import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import jp.kirin3.anytimeqiita.ui.sample.view.SampleContract
import jp.kirin3.anytimeqiita.usecase.SampleUseCase
import kirin3.jp.mljanken.util.LogUtils.LOGE
import javax.inject.Inject
import javax.inject.Named

class SamplePresenterImpl @Inject constructor(
    private val sampleUseCase: SampleUseCase,
    @Named("ui") private val uiScheduler: Scheduler
) : SamplePresenter {


    private lateinit var view: SampleContract
    private lateinit var viewModel: SampleViewModel

    private val disposables = CompositeDisposable()
    private var nowLoading = false


    override fun setup(view: SampleContract, viewModel: SampleViewModel) {
        this.view = view
        this.viewModel = viewModel
    }

    override fun initTextEdit() {
        TODO("Not yet implemented")
    }

    override fun greet(): String {
        sampleUseCase.outLog()
        return "こんにちわ"
    }

    override fun loadData() {
        sampleUseCase
            .loadSampleDataResult("code")
            .observeOn(uiScheduler)
            .doOnSubscribe {
                nowLoading = true
            }
            .doFinally {
                nowLoading = false
            }
            .subscribe({ result ->
                view.showButton(result.id)
            }, { e ->
                LOGE("Failed to load invitation code. ${e.message}")
            })
            .addTo(disposables)


    }

}