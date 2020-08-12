package jp.kirin3.anytimeqiita.ui.folders

import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import jp.kirin3.anytimeqiita.data.FoldersBasicData
import jp.kirin3.anytimeqiita.ui.stocks.FoldersDataSource
import jp.kirin3.anytimeqiita.ui.stocks.FoldersRepository

class FoldersPresenter(
    private val foldersRepository: FoldersRepository,
    private val foldersView: FoldersContract.View
) : FoldersContract.Presenter {

    // FragmentのpresenterにViewを設定
    init {
        foldersView.presenter = this
    }

    override fun createFirstFolders() {
        foldersRepository.createFirstFolders()
    }

    override fun createNewFolder(seqid:Int,name:String) {
        foldersRepository.createNewFolder(seqid,name)
    }

    override fun readFolders() {
        foldersRepository.getFolders(object : FoldersDataSource.LoadTasksCallback {
            override fun onFoldersLoaded(folders: List<FoldersBasicData>) {
                foldersView.showFoldersRecyclerView(folders.toMutableList())
            }
            override fun onDataNotAvailable() {
            }
        })
    }

    override fun startNotLoggedIn() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun getMessage() {
//       getTasks()
    }


    override fun processAccessToken(code: String) {
    }
}

