package jp.kirin3.anytimeqiita.database

import io.realm.Realm
import io.realm.kotlin.where
import jp.kirin3.anytimeqiita.data.StocksResponseData

object StocksDatabase {

    fun insertStocksDataList(userDataList: List<StocksResponseData>?) {
        if (userDataList == null) return


        xxxxxxxxxxxxxxxxxxxx

        var realm = Realm.getDefaultInstance()

        realm.beginTransaction()
        for (userData in userDataList) {
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

    fun selectStocksData(): List<StocksResponseData>? {

        var realm = Realm.getDefaultInstance()

        val stocks = realm.where<StocksResponseData>().findAll()
        if (stocks.count() == 0) return null
        val stocksList = realm.copyFromRealm(stocks)

        realm.close()

        return stocksList

    }
}