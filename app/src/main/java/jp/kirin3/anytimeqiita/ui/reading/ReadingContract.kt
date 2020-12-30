package jp.kirin3.anytimeqiita.ui.reading

import jp.kirin3.anytimeqiita.presenter.BasePresenter
import jp.kirin3.anytimeqiita.view.BaseView

/**
 * This specifies the contract between the view and the presenter.
 */
interface ReadingContract {

    interface View : BaseView<Presenter> {
    }

    interface Presenter : BasePresenter {
        fun addReadingTimeToDb(readingTime: Int)

        fun setRandamDemoReadingTime()
    }
}
