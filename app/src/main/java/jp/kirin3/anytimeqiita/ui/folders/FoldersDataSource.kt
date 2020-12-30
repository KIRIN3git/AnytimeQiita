package jp.kirin3.anytimeqiita.ui.stocks

import jp.kirin3.anytimeqiita.data.FoldersData

interface FoldersDataSource {
    interface LoadTasksCallback {
        fun onFoldersLoaded(stocks: List<FoldersData>)
        fun onDataNotAvailable()
    }
}