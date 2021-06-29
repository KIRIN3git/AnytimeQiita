package jp.kirin3.anytimeqiita.usecase

import io.reactivex.Single
import jp.kirin3.anytimeqiita.data.StocksResponseData
import jp.kirin3.anytimeqiita.ui.stocks.StocksDataSource

interface StocksUseCase {

    fun isLoadCompleted(): Boolean

    fun getStockListFromDb(): List<StocksResponseData>?

    fun resetStockListFromDb()

    fun loadStockList(
        userId: String
    ): Single<List<StocksResponseData>>

    fun loadStockListOld(
        userId: String?,
        callback: StocksDataSource.LoadTasksCallback
    )

    fun getPageCount(): Int

    fun addOnePageCount()

    fun resetPageCount()

    fun setStockLoadCompleted(isCompleted: Boolean)

}