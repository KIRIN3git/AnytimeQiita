package jp.kirin3.anytimeqiita.usecase

import jp.kirin3.anytimeqiita.data.StocksResponseData
import jp.kirin3.anytimeqiita.ui.stocks.StocksDataSource

interface StocksUseCase {

    fun isLoadCompleted(): Boolean

    fun getStockListFromDb(): List<StocksResponseData>?

    fun resetStockListFromDb()

    fun loadStockList(
        userId: String?,
        callback: StocksDataSource.LoadTasksCallback
    )

    fun resetPageCount()
}