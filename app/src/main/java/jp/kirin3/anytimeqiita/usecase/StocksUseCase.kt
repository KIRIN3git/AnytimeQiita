package jp.kirin3.anytimeqiita.usecase

import io.reactivex.Single
import jp.kirin3.anytimeqiita.data.StocksResponseData

interface StocksUseCase {

    fun isLoadCompleted(): Boolean

    fun getStockListFromDb(position:Int): List<StocksResponseData>?

    fun resetStockListFromDb()

    fun loadStockList(
        userId: String
    ): Single<List<StocksResponseData>>

//    fun loadStockListOld(
//        userId: String?,
//        callback: StocksDataSource.LoadTasksCallback
//    )

    fun getPageCount(): Int

    fun getLoadedStocksCount(): Int

    fun addOnePageCount()

    fun resetPageCount()

    fun setStockLoadCompleted(isCompleted: Boolean)

}