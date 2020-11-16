package jp.kirin3.anytimeqiita.ui.setting

import android.content.Context
import jp.kirin3.anytimeqiita.data.FoldersData
import jp.kirin3.anytimeqiita.presenter.BasePresenter
import jp.kirin3.anytimeqiita.view.BaseView

/**
 * This specifies the contract between the view and the presenter.
 */
interface SettingContract {

    interface View : BaseView<Presenter> {
        fun setLoggingModeInterface(login: Boolean)
    }

    interface Presenter : BasePresenter {

        fun loadQiitaLoginInfo(context: Context?)
    }
}
