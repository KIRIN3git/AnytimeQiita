package jp.kirin3.anytimeqiita.ui.stocks

import androidx.lifecycle.ViewModel
import jp.kirin3.anytimeqiita.api.ApiClient
import jp.kirin3.anytimeqiita.data.StocksResponseData
import jp.kirin3.anytimeqiita.database.StocksDatabase
import jp.kirin3.anytimeqiita.model.AuthenticatedUserModel
import kirin3.jp.mljanken.util.LogUtils
import kirin3.jp.mljanken.util.LogUtils.LOGD

class StocksRepository() : ViewModel(), StocksDataSource {

    private var cacheStocksList: MutableList<StocksResponseData>? = null
    private var pageCount = 1
    private val READ_COUNT = 30

    companion object {

        private var INSTANCE: StocksRepository? = null


        fun setStocksToCache(): StocksRepository {
            return INSTANCE ?: StocksRepository()
                .apply { INSTANCE = this }
        }


        /**
         * Returns the single instance of this class, creating it if necessary.

         * @param tasksRemoteDataSource the backend data source
         * *
         * @param tasksLocalDataSource  the device storage data source
         * *
         * @return the [TasksRepository] instance
         */
        fun getInstance(): StocksRepository {
            return INSTANCE ?: StocksRepository()
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


    fun getStocksFromAny(callback: StocksDataSource.LoadTasksCallback) {

        getStocksFromDB()?.let {
            callback.onStocksLoaded(it)
        } ?: let {
            loadStocks(
                AuthenticatedUserModel.getAuthenticatedUserIdFromCache(),
                false,
                object : StocksDataSource.LoadTasksCallback {
                    override fun onStocksLoaded(stocks: List<StocksResponseData>) {
                        callback.onStocksLoaded(stocks)
                    }

                    override fun onDataNotAvailable() {
                        callback.onDataNotAvailable()
                    }
                }
            )
        }
    }


    fun setStocksToCache(stocksList: List<StocksResponseData>?, nextFlg: Boolean) {
        if (stocksList == null) return

        if (nextFlg && stocksList != null) {
            cacheStocksList!!.addAll(stocksList)
        }
        cacheStocksList = stocksList.toMutableList()
    }

    fun getStocksFromCache(): List<StocksResponseData>? {
        return cacheStocksList
    }

    fun getStocksFromDB(): List<StocksResponseData>? {
        StocksDatabase.selectStocksData()?.let {
            //            setStocksToCache(it)
            setPageCount(it)
            return it
        }
        return null
    }

    fun setPageCount(stocks: List<StocksResponseData>) {
        pageCount = stocks.size + 1
    }

    fun loadStocks(
        userId: String?,
        loadFirst:Boolean,
        callback: StocksDataSource.LoadTasksCallback
    ) {

        LOGD("pageCount " + pageCount + " READ_COUNT " + READ_COUNT)

        if(loadFirst == true){
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

                    callback.onStocksLoaded(responseData)
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