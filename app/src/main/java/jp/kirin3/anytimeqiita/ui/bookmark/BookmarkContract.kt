package jp.kirin3.anytimeqiita.ui.bookmark

import jp.kirin3.anytimeqiita.presenter.BasePresenter
import jp.kirin3.anytimeqiita.view.BaseView

/**
 * This specifies the contract between the view and the presenter.
 */
interface BookmarkContract {

    interface View : BaseView<Presenter> {
        fun showMessage(msg: String)
    }

    interface Presenter : BasePresenter {

        fun getMessage()
        fun processAccessToken(code: String)
    }
}
