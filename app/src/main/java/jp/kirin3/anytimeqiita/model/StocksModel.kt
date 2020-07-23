package jp.kirin3.anytimeqiita.model

import android.content.Context
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import jp.kirin3.anytimeqiita.api.ApiClient
import jp.kirin3.anytimeqiita.data.StocksResponseData
import jp.kirin3.anytimeqiita.database.StocksDatabase
import kirin3.jp.mljanken.util.LogUtils

class StocksModel() {

    companion object {

        private var cacheStocksList: List<StocksResponseData>? = null

        fun setStocksToCache(stocksList: List<StocksResponseData>?) {
            cacheStocksList = stocksList
        }


        fun loadStocks(context: Context?, userId: String?, page: String,refreshLayout: SwipeRefreshLayout) {
            if (context == null) return

            ApiClient.fetchStocks(
                userId,
                page,
                object : ApiClient.StocksApiCallback {
                    override fun onTasksLoaded(responseData: List<StocksResponseData>) {
//                        LogUtils.LOGI("GET AuthenticatedUser responseData.id = " + responseData.body)

                        // データをキャッシュ保存
                        setStocksToCache(
                            responseData
                        )

                        // データをデータベース保存
                        StocksDatabase.insertStocksDataList(responseData)


                        val dataList = StocksDatabase.selectStocksData()
                        if (dataList != null) {
                            for (data in dataList) {
                                LogUtils.LOGD("XXXXXXXXA " + data.title)
                            }
                        }
                        refreshLayout.setRefreshing(false);

//                        latch.apply { countDown() }
                    }

                    override fun onDataNotAvailable() {
                        LogUtils.LOGI("Fail fetchAuthenticatedUser")

                        setStocksToCache(
                            null
                        )
                        StocksDatabase.deleteStocksDataList()

//                        latch.apply { countDown() }
                    }
                })
        }
    }
}