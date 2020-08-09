package jp.kirin3.anytimeqiita.ui.stocks

import jp.kirin3.anytimeqiita.data.FoldersBasicData
import jp.kirin3.anytimeqiita.data.StocksResponseData

interface FoldersDataSource {
    interface LoadTasksCallback {

        fun onFoldersLoaded(stocks: List<FoldersBasicData>)

        fun onDataNotAvailable()
    }
}