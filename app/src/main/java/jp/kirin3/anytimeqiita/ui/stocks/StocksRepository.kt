package jp.kirin3.anytimeqiita.ui.stocks

import android.content.Context
import androidx.lifecycle.ViewModel
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import jp.kirin3.anytimeqiita.api.ApiClient
import jp.kirin3.anytimeqiita.api.StocksClient
import jp.kirin3.anytimeqiita.data.StocksResponseData
import jp.kirin3.anytimeqiita.database.StocksDatabase
import jp.kirin3.anytimeqiita.di.RetrofitFactory
import kirin3.jp.mljanken.util.LogUtils
import kirin3.jp.mljanken.util.LogUtils.LOGD
import kirin3.jp.mljanken.util.SettingsUtils

class StocksRepository(
    private val retrofitFactory: RetrofitFactory
) : ViewModel(), StocksDataSource {

    private var cacheStocksList: MutableList<StocksResponseData>? = null
    private var pageCount = 1

    companion object {
        // 一度で取得するstock数
        private const val ONE_TIME_STOCKS_NUM = 10
        private var INSTANCE: StocksRepository? = null

        /**
         * Returns the single instance of this class, creating it if necessary.

         * @param tasksRemoteDataSource the backend data source
         * *
         * @param tasksLocalDataSource  the device storage data source
         * *
         * @return the [TasksRepository] instance
         */
//        fun getInstance(): StocksRepository {
//            return INSTANCE ?: StocksRepository()
//                .apply { INSTANCE = this }
//        }

        /**
         * Used to force [getInstance] to create a new instance
         * next time it's called.
         */
        fun destroyInstance() {
            INSTANCE = null
        }
    }

//    fun getStocksFromDbOrApi(callback: StocksDataSource.LoadTasksCallback) {
//        getStocksFromDB()?.let {
//            // DBから取得可能
//            callback.onLoadSuccess(it)
//        } ?: loadStockList(
//            AuthenticatedUserModel.getAuthenticatedUserIdFromCache(),
//            false,
//            object : StocksDataSource.LoadTasksCallback {
//                override fun onLoadSuccess(stocks: List<StocksResponseData>) {
//                    callback.onLoadSuccess(stocks)
//                }
//
//                override fun onLoadNoData() {
//                    callback.onLoadNoData()
//                }
//
//                override fun onLoadFailure() {
//                    callback.onLoadFailure()
//                }
//            }
//        )
//    }


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

    fun getStocksFromDb(): List<StocksResponseData>? {
        StocksDatabase.selectStocksData()?.let {
            return it
        }
        return null
    }

    fun deleteStocksFromDb() {
        StocksDatabase.deleteStocksDataList()
    }

    fun setPageCount(context: Context, pageCount: Int) {
        SettingsUtils.setStockPageCount(context, pageCount)
    }

    fun getPageCount(context: Context) {
        SettingsUtils.getStockPageCount(context)
    }

    /**
     * QiitaAPIからストック情報を取得
     * 取得後はDBに保存を行う
     */
    fun loadStockList(
        userId: String,
        pageCount: Int
    ): Single<List<StocksResponseData>> {
        val api = StocksClient()
        return api.fetchStocks(
            retrofitFactory,
            userId,
            pageCount.toString(),
            ONE_TIME_STOCKS_NUM.toString()
        )
            .subscribeOn(Schedulers.io())
//            .observeOn(Schedulers.single())
            .doAfterSuccess {
//                it.updateChache(context) //WWW
                LOGD("pageCount $pageCount READ_COUNT $ONE_TIME_STOCKS_NUM")
            }
        LOGD("pageCount $pageCount READ_COUNT $ONE_TIME_STOCKS_NUM")
    }

    fun loadStockListOld(
        userId: String?,
        pageCount: Int,
        callback: StocksDataSource.LoadTasksCallback
    ) {

        LOGD("pageCount $pageCount READ_COUNT $ONE_TIME_STOCKS_NUM")

        ApiClient.fetchStocksOld(
            userId,
            pageCount.toString(),
            ONE_TIME_STOCKS_NUM.toString(),
            object : ApiClient.StocksApiCallback {
                override fun onFetchSuccess(responseData: List<StocksResponseData>) {
                    callback.onLoadSuccess(responseData)
                }

                override fun onFetchNoData() {
                    callback.onLoadNoData()
                }

                override fun onFetchFailure() {
                    LogUtils.LOGI("Fail fetchAuthenticatedUser")

                    callback.onLoadFailure()
                }
            })
    }
}