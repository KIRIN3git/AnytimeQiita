package jp.kirin3.anytimeqiita.ui.stocks

import androidx.lifecycle.ViewModel
import jp.kirin3.anytimeqiita.data.FoldersData
import jp.kirin3.anytimeqiita.data.StocksResponseData
import jp.kirin3.anytimeqiita.database.FoldersDatabase
import java.util.*

class GraphRepository() : ViewModel(), GraphDataSource {

    private var pageCount = 1

    private val FIRST_FOLDERS: List<FoldersData> =
        listOf(
            FoldersData(0, "今読む", Date()),
            FoldersData(1, "定期的に読む", Date()),
            FoldersData(2, "後でまとめる", Date())
        )

    companion object {

        private var INSTANCE: GraphRepository? = null


        fun getInstance(): GraphRepository {
            return INSTANCE ?: GraphRepository()
                .apply { INSTANCE = this }
        }

        /**
         * Used to force [getInstance] to create a new instance
         * next time it's called.
         */
        fun destroyInstance() {
            INSTANCE = null
        }
    }

    fun getFolders(callback: FoldersDataSource.LoadTasksCallback) {
        FoldersDatabase.selectFoldersData()?.let {
            callback.onFoldersLoaded(it)
        } ?: let {
            callback.onDataNotAvailable()
        }
    }


    fun createFirstFolders() {
        FoldersDatabase.insertFoldersDataList(FIRST_FOLDERS)
    }

    fun createNewFolder(seqid: Int, name: String) {

        val folder: List<FoldersData> =
            listOf(
                FoldersData(seqid, name, Date())
            )
        FoldersDatabase.insertFoldersDataList(folder)
    }

    fun setPageCount(stocks: List<StocksResponseData>) {
        pageCount = stocks.size + 1
    }

}