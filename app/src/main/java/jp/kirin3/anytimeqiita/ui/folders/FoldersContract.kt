package jp.kirin3.anytimeqiita.ui.folders

import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import jp.kirin3.anytimeqiita.data.FoldersBasicData
import jp.kirin3.anytimeqiita.presenter.BasePresenter
import jp.kirin3.anytimeqiita.view.BaseView

/**
 * This specifies the contract between the view and the presenter.
 */
interface FoldersContract {

    interface View : BaseView<Presenter> {
        fun showMessage(msg: String)
        fun showFoldersRecyclerView(
            folders: MutableList<FoldersBasicData>?
        )
    }

    interface Presenter : BasePresenter {


        fun createFirstFolders(foldersRecyclerView:RecyclerView)
        fun readFolders()

        fun startNotLoggedIn()

        fun getMessage()
        fun processAccessToken(code: String)
    }
}
