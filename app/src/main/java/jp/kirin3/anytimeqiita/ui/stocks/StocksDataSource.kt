package jp.kirin3.anytimeqiita.ui.stocks

import jp.kirin3.anytimeqiita.data.StocksResponseData

interface StocksDataSource {
    interface LoadTasksCallback {

        fun onStocksLoaded(stocks: List<StocksResponseData>)

        fun onDataNotAvailable()
    }
}