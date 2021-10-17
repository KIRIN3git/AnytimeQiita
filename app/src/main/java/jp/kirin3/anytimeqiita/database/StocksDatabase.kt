package jp.kirin3.anytimeqiita.database

import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.where
import jp.kirin3.anytimeqiita.data.SpinnerData
import jp.kirin3.anytimeqiita.data.StocksResponseData
import kirin3.jp.mljanken.util.LogUtils.LOGI

object StocksDatabase {

    private const val ID = "id"
    private var sequence = 0

    fun insertStocksDataList(pageCount: Int, userDataList: List<StocksResponseData>?) {
        if (userDataList == null) return

        var realm = Realm.getDefaultInstance()

        realm.beginTransaction()
        if (pageCount == 1) sequence = 0

        for (userData in userDataList) {
            userData.sequence = sequence++
            realm.insert(userData)
        }
        realm.commitTransaction()

        realm.close()
    }

    fun deleteStocksDataList() {

        var realm = Realm.getDefaultInstance()

        val stocksData = realm.where<StocksResponseData>().findAll()

        realm.beginTransaction()
        stocksData.deleteAllFromRealm()
        realm.commitTransaction()

        realm.close()
    }

    fun selectStocksData(orderPosition: Int, sortPosition: Int): List<StocksResponseData>? {

        var realm = Realm.getDefaultInstance()
        val order = when (orderPosition) {
            0 -> Sort.ASCENDING
            else -> Sort.DESCENDING
        }
        val stocks =
            realm.where<StocksResponseData>().findAll()
                .sort(SpinnerData.values().find { it.ordinal == sortPosition }?.column,order)

        LOGI("select stocks count = $stocks.count()")

        if (stocks.count() == 0) {
            realm.close()
            return null
        }
        val stocksList = realm.copyFromRealm(stocks)

        realm.close()

        return stocksList
    }

    fun selectStocksDataById(id: String): StocksResponseData? {

        var realm = Realm.getDefaultInstance()

        val stocks = realm.where<StocksResponseData>()
            .equalTo(ID, id)
            .findAll()
        if (stocks.count() != 1) {
            realm.close()
            return null
        }
        val stocksList = realm.copyFromRealm(stocks)

        realm.close()

        return stocksList[0]
    }
}