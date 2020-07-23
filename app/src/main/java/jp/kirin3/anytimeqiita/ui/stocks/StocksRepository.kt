package jp.kirin3.anytimeqiita.ui.stocks

import androidx.lifecycle.ViewModel
import jp.kirin3.anytimeqiita.api.ApiClient
import jp.kirin3.anytimeqiita.data.StocksResponseData
import jp.kirin3.anytimeqiita.database.StocksDatabase
import jp.kirin3.anytimeqiita.model.AuthenticatedUserModel
import kirin3.jp.mljanken.util.LogUtils

class StocksRepository() : ViewModel(), StocksDataSource {

    private var cacheStocksList: List<StocksResponseData>? = null

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
        getStocksFromCache()?.let {
            callback.onStocksLoaded(it)
        } ?: let {
            getStocksFromDB()?.let {
                callback.onStocksLoaded(it)
            } ?: let {
                loadStocks(
                    AuthenticatedUserModel.getAuthenticatedUserIdFromCache(),
                    "1",
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
    }


    fun setStocksToCache(stocksList: List<StocksResponseData>?) {
        cacheStocksList = stocksList
    }

    fun getStocksFromCache(): List<StocksResponseData>? {
        return cacheStocksList
    }

    fun getStocksFromDB(): List<StocksResponseData>? {
        StocksDatabase.selectStocksData()?.let {
            setStocksToCache(it)
            return it
        }
        return null
    }

    fun loadStocks(
        userId: String?,
        page: String,
        callback: StocksDataSource.LoadTasksCallback
    ) {

        ApiClient.fetchStocks(
            userId,
            page,
            object : ApiClient.StocksApiCallback {
                override fun onTasksLoaded(responseData: List<StocksResponseData>) {
//                        LogUtils.LOGI("GET AuthenticatedUser responseData.id = " + responseData.body)

                    // データをキャッシュ保存
                    setStocksToCache(
                        responseData
                    )

                    // データをデータベース保存
                    StocksDatabase.insertStocksDataList(responseData)


                    val dataList = StocksDatabase.selectStocksData()
                    if (dataList != null) {
                        for (data in dataList) {
                            LogUtils.LOGD("XXXXXXXXA " + data.title)
                        }
                    }

                    callback.onStocksLoaded(responseData)
//                    refreshLayout.setRefreshing(false);

//                        latch.apply { countDown() }
                }

                override fun onDataNotAvailable() {
                    LogUtils.LOGI("Fail fetchAuthenticatedUser")

                    setStocksToCache(
                        null
                    )
                    StocksDatabase.deleteStocksDataList()


                    callback.onDataNotAvailable()
//                        latch.apply { countDown() }
                }
            })
    }


}