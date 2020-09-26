package jp.kirin3.anytimeqiita.ui.folders

import jp.kirin3.anytimeqiita.data.FoldersData
import jp.kirin3.anytimeqiita.database.FoldersDatabase
import jp.kirin3.anytimeqiita.model.FoldersModel
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

    override fun createNewFolder(seqid: Int, name: String) {
        foldersRepository.createNewFolder(seqid, name)
    }

    override fun editFolderName(seqid: Int, name: String, position: Int) {

        var newCache = FoldersModel.getFoldersFromCache()?.also{
            it[position].name = name
        }
        FoldersDatabase.deleteFoldersDataList()
        FoldersDatabase.insertFoldersDataList(newCache)
        readFolders()
    }

    override fun readFolders() {
        foldersRepository.getFolders(object : FoldersDataSource.LoadTasksCallback {
            override fun onFoldersLoaded(folders: List<FoldersData>) {
                foldersView.showFoldersRecyclerView(folders.toMutableList())
                FoldersModel.setFoldersToCache(folders)
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

