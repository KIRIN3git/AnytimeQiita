package jp.kirin3.anytimeqiita.database

import io.realm.Realm
import io.realm.kotlin.where
import jp.kirin3.anytimeqiita.data.StocksResponseData
import kirin3.jp.mljanken.util.LogUtils.LOGI

object StocksDatabase {

    fun insertStocksDataList(userDataList: List<StocksResponseData>?) {
        if (userDataList == null) return

        var realm = Realm.getDefaultInstance()

        val nowUserData = realm.where<StocksResponseData>().findAll()

        realm.beginTransaction()
//        if (resetFlg) {
//            nowUserData.deleteAllFromRealm()
//        }
        for (userData in userDataList) {
            LOGI("YYYYYYYYYYYYYYY " + userData.title)
            realm.insert(userData)
        }
        realm.commitTransaction()

        realm.close()
    }

    fun deleteStocksDataList() {

        var realm = Realm.getDefaultInstance()

        val nowUserData = realm.where<StocksResponseData>().findAll()

        realm.beginTransaction()
        nowUserData.deleteAllFromRealm()
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