package jp.kirin3.anytimeqiita.ui.stocks

import android.content.Context
import androidx.lifecycle.ViewModel
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import jp.kirin3.anytimeqiita.api.StocksClient
import jp.kirin3.anytimeqiita.data.StocksResponseData
import jp.kirin3.anytimeqiita.database.StocksDatabase
import jp.kirin3.anytimeqiita.di.RetrofitFactory
import kirin3.jp.mljanken.util.LogUtils.LOGD
import kirin3.jp.mljanken.util.SharedPreferencesUtils

class StocksRepository(
    private val retrofitFactory: RetrofitFactory
) : ViewModel(), StocksDataSource {

    private var cacheStocksList: MutableList<StocksResponseData>? = null

    companion object {
        // 一度で取得するstock数

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
        SharedPreferencesUtils.setStockPageCount(context, pageCount)
    }

    fun getPageCount(context: Context) {
        SharedPreferencesUtils.getStockPageCount(context)
    }

    /**
     * QiitaAPIからストック情報を取得
     * 取得後はDBに保存を行う
     */
    fun loadStockList(
        userId: String,
        pageCount: Int,
        oneTimeStocksNum:Int
    ): Single<List<StocksResponseData>> {
        val api = StocksClient()
        return api.fetchStocks(
            retrofitFactory,
            userId,
            pageCount.toString(),
            oneTimeStocksNum.toString()
        )
            .subscribeOn(Schedulers.io())
            .doAfterSuccess {
                LOGD("pageCount $pageCount READ_COUNT $oneTimeStocksNum")
            }
    }

//    fun loadStockListOld(
//        userId: String?,
//        pageCount: Int,
//        callback: StocksDataSource.LoadTasksCallback
//    ) {
//
//        LOGD("pageCount $pageCount READ_COUNT $ONE_TIME_STOCKS_NUM")
//
//        ApiClient.fetchStocksOld(
//            userId,
//            pageCount.toString(),
//            ONE_TIME_STOCKS_NUM.toString(),
//            object : ApiClient.StocksApiCallback {
//                override fun onFetchSuccess(responseData: List<StocksResponseData>) {
//                    callback.onLoadSuccess(responseData)
//                }
//
//                override fun onFetchNoData() {
//                    callback.onLoadNoData()
//                }
//
//                override fun onFetchFailure() {
//                    LogUtils.LOGI("Fail fetchAuthenticatedUser")
//
//                    callback.onLoadFailure()
//                }
//            })
//    }
}