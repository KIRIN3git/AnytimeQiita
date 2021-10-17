package jp.kirin3.anytimeqiita.usecase.impl

import android.content.Context
import io.reactivex.Single
import jp.kirin3.anytimeqiita.data.StocksResponseData
import jp.kirin3.anytimeqiita.ui.stocks.StocksRepository
import jp.kirin3.anytimeqiita.usecase.StocksUseCase
import kirin3.jp.mljanken.util.SharedPreferencesUtils
import javax.inject.Inject

class StocksUseCaseImpl @Inject constructor(
    private val context: Context,
    private val repository: StocksRepository
) : StocksUseCase {

    companion object {
        const val RESET_PAGE_COUNT = 1
        private const val ONE_TIME_STOCKS_NUM = 100
    }

    override fun isLoadCompleted(): Boolean {
        return SharedPreferencesUtils.getStockLoadingCompleted(context)
    }

    override fun getStockListFromDb(orderPosition: Int,sortPosition: Int): List<StocksResponseData>? {
        return repository.getStocksFromDb(orderPosition,sortPosition)
    }

    override fun resetStockListFromDb() {
        return repository.deleteStocksFromDb()
    }

    override fun loadStockList(
        userId: String
    ): Single<List<StocksResponseData>> {
        val pageCount = getPageCount()
        return repository.loadStockList(userId, pageCount, ONE_TIME_STOCKS_NUM)
            .doAfterSuccess {
            }
    }

//    override fun loadStockListOld(
//        userId: String?,
//        callback: StocksDataSource.LoadTasksCallback
//    ) {
//        val pageCount = SettingsUtils.getStockPageCount(context)
//
//        repository.loadStockListOld(
//            userId,
//            pageCount,
//            object : StocksDataSource.LoadTasksCallback {
//                override fun onLoadSuccess(stocks: List<StocksResponseData>) {
//                    // データベース保存
//                    StocksDatabase.insertStocksDataList(stocks)
//                    addOnePageCount()
//                    callback.onLoadSuccess(stocks)
//                }
//
//                // 取得データがない = 全データ取得完了
//                override fun onLoadNoData() {
//                    SettingsUtils.setStockLoadingCompleted(context, true)
//                    callback.onLoadNoData()
//                }
//
//                override fun onLoadFailure() {
//                    // データベース削除
//                    StocksDatabase.deleteStocksDataList()
//                    callback.onLoadFailure()
//                }
//            })
//    }

    override fun getPageCount(): Int {
        return SharedPreferencesUtils.getStockPageCount(context)
    }
    override fun getLoadedStocksCount(): Int {
        return (SharedPreferencesUtils.getStockPageCount(context) - 1) * ONE_TIME_STOCKS_NUM
    }

    override fun addOnePageCount() {
        SharedPreferencesUtils.setStockPageCount(context, SharedPreferencesUtils.getStockPageCount(context) + 1)
    }

    override fun resetPageCount() {
        SharedPreferencesUtils.setStockPageCount(context, RESET_PAGE_COUNT)
    }

    override fun setStockLoadCompleted(isCompleted: Boolean) {
        SharedPreferencesUtils.setStockLoadingCompleted(context, isCompleted)
    }
}