package jp.kirin3.anytimeqiita.ui.folders

import jp.kirin3.anytimeqiita.data.FoldersData
import jp.kirin3.anytimeqiita.presenter.BasePresenter
import jp.kirin3.anytimeqiita.view.BaseView

/**
 * This specifies the contract between the view and the presenter.
 */
interface FoldersContract {

    interface View : BaseView<Presenter> {
        fun showMessage(msg: String)
        fun showFoldersRecyclerView(
            folders: MutableList<FoldersData>
        )
    }

    interface Presenter : BasePresenter {
        fun createFirstFolders()
        fun createNewFolder(seqid: Int, name: String)
        fun editFolderName(seqid: Int, name: String, position: Int)
        fun readFolders()
        fun startNotLoggedIn()
        fun getMessage()
        fun processAccessToken(code: String)
    }
}
