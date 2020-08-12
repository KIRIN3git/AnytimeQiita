package jp.kirin3.anytimeqiita.ui.stocks

import androidx.lifecycle.ViewModel
import jp.kirin3.anytimeqiita.api.ApiClient
import jp.kirin3.anytimeqiita.data.FoldersBasicData
import jp.kirin3.anytimeqiita.data.StocksResponseData
import jp.kirin3.anytimeqiita.database.FoldersDatabase
import jp.kirin3.anytimeqiita.database.StocksDatabase
import kirin3.jp.mljanken.util.LogUtils
import kirin3.jp.mljanken.util.LogUtils.LOGD
import kirin3.jp.mljanken.util.SettingsUtils
import java.util.*

class FoldersRepository() : ViewModel(), FoldersDataSource {

    private var cacheStocksList: MutableList<StocksResponseData>? = null
    private var pageCount = 1
    private val READ_COUNT = 30

    private val FIRST_FOLDERS: List<FoldersBasicData> =
        listOf(
            FoldersBasicData(0, "今読む", Date()),
            FoldersBasicData(1, "定期的に読む", Date()),
            FoldersBasicData(2, "後でまとめる", Date())
        )

    companion object {

        /**
         * FIRST_FOLDERSの数に起因する
         */
        val FOLDERS_FIRST_SEQID = 3

        private var INSTANCE: FoldersRepository? = null


        fun getInstance(): FoldersRepository {
            return INSTANCE ?: FoldersRepository()
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

        val folder: List<FoldersBasicData> =
            listOf(
                FoldersBasicData(seqid, name, Date())
            )
        FoldersDatabase.insertFoldersDataList(folder)
    }



    fun getStocksFromAny(callback: FoldersDataSource.LoadTasksCallback) {

//        getStocksFromDB()?.let {
//            callback.onStocksLoaded(it)
//        } ?: let {
//            loadStocks(
//                AuthenticatedUserModel.getAuthenticatedUserIdFromCache(),
//                false,
//                object : FoldersDataSource.LoadTasksCallback {
//                    override fun onStocksLoaded(stocks: List<StocksResponseData>) {
//                        callback.onStocksLoaded(stocks)
//                    }
//
//                    override fun onDataNotAvailable() {
//                        callback.onDataNotAvailable()
//                    }
//                }
//            )
//        }
    }

    fun setPageCount(stocks: List<StocksResponseData>) {
        pageCount = stocks.size + 1
    }

    fun loadStocks(
        userId: String?,
        loadFirst: Boolean,
        callback: FoldersDataSource.LoadTasksCallback
    ) {

        LOGD("pageCount " + pageCount + " READ_COUNT " + READ_COUNT)

        if (loadFirst == true) {
            pageCount = 1
        }
        ApiClient.fetchStocks(
            userId,
            pageCount.toString(),
            READ_COUNT.toString(),
            object : ApiClient.StocksApiCallback {
                override fun onTasksLoaded(responseData: List<StocksResponseData>) {

                    // データをデータベース保存
                    StocksDatabase.insertStocksDataList(responseData)

                    pageCount++
//                    val dataList = StocksDatabase.selectStocksData()
//                    if (dataList != null) {
//                        for (data in dataList) {
//                            LogUtils.LOGD("XXXXXXXXA " + data.title)
//                        }
//                    }

//                    callback.onStocksLoaded(responseData)
//                    refreshLayout.setRefreshing(false);

//                        latch.apply { countDown() }
                }

                override fun onDataNotAvailable() {
                    LogUtils.LOGI("Fail fetchAuthenticatedUser")

                    StocksDatabase.deleteStocksDataList()

                    callback.onDataNotAvailable()
//                        latch.apply { countDown() }
                }
            })
    }
}