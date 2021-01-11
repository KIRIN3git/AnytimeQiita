package jp.kirin3.anytimeqiita.ui.stocks

import jp.kirin3.anytimeqiita.data.ReadingTimeData

interface GraphDataSource {
    interface LoadTaskCallback {
        fun onGraphLoaded(readingTimeData: ReadingTimeData)
        fun onDataNotAvailable()
    }

    interface LoadTaskListCallback {
        fun onGraphListLoaded(readingTimeDataList: List<ReadingTimeData>)
        fun onDataNotAvailable()
    }
}