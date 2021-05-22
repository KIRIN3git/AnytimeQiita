package jp.kirin3.anytimeqiita.ui.stocks

import jp.kirin3.anytimeqiita.data.StocksResponseData

interface StocksDataSource {
    interface LoadTasksCallback {
        fun onLoadSuccess(stocks: List<StocksResponseData>)
        fun onLoadNoData()
        fun onLoadFailure()
    }

    interface GetTasksCallback {
        fun onLoadSuccess(stocks: List<StocksResponseData>)
        fun onLoadNoData()
        fun onLoadFailure()
    }
}