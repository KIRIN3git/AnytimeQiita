package jp.kirin3.anytimeqiita.usecase.impl

import android.content.Context
import jp.kirin3.anytimeqiita.data.StocksResponseData
import jp.kirin3.anytimeqiita.database.StocksDatabase
import jp.kirin3.anytimeqiita.ui.stocks.StocksDataSource
import jp.kirin3.anytimeqiita.ui.stocks.StocksRepository
import jp.kirin3.anytimeqiita.usecase.StocksUseCase
import kirin3.jp.mljanken.util.SettingsUtils
import javax.inject.Inject

class StocksUseCaseImpl @Inject constructor(
    private val context: Context,
    private val repository: StocksRepository
) : StocksUseCase {

    companion object {
        const val RESET_PAGE_COUNT = 1
    }

    override fun isLoadCompleted(): Boolean {
        return SettingsUtils.getStockLoadingCompleted(context)
    }

    override fun getStockListFromDb(): List<StocksResponseData>? {
        return repository.getStocksFromDb()
    }

    override fun resetStockListFromDb() {
        return repository.deleteStocksFromDb()
    }

    override fun loadStockList(
        userId: String?,
        callback: StocksDataSource.LoadTasksCallback
    ) {
        val pageCount = SettingsUtils.getStockPageCount(context)

        repository.loadStockList(
            userId,
            pageCount,
            object : StocksDataSource.LoadTasksCallback {
                override fun onLoadSuccess(stocks: List<StocksResponseData>) {
                    // データベース保存
                    StocksDatabase.insertStocksDataList(stocks)
                    addOnePageCount()
                    callback.onLoadSuccess(stocks)
                }

                // 取得データがない = 全データ取得完了
                override fun onLoadNoData() {
                    SettingsUtils.setStockLoadingCompleted(context, true)
                    callback.onLoadNoData()
                }

                override fun onLoadFailure() {
                    // データベース削除
                    StocksDatabase.deleteStocksDataList()
                    callback.onLoadFailure()
                }
            })
    }

    private fun addOnePageCount() {
        SettingsUtils.setStockPageCount(context, SettingsUtils.getStockPageCount(context) + 1)
    }

    override fun resetPageCount() {
        SettingsUtils.setStockPageCount(context, RESET_PAGE_COUNT)
    }
}