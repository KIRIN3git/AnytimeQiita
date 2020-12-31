package jp.kirin3.anytimeqiita.ui.stocks

import androidx.lifecycle.ViewModel
import jp.kirin3.anytimeqiita.data.ReadingTimeData
import jp.kirin3.anytimeqiita.database.ReadingTimeDatabase
import kirin3.jp.mljanken.util.TimeUtils
import java.util.*

class GraphRepository() : ViewModel(), GraphDataSource {


    companion object {

        private var INSTANCE: GraphRepository? = null

        fun getInstance(): GraphRepository {
            return INSTANCE ?: GraphRepository()
                .apply { INSTANCE = this }
        }

        /**
         * Used to force [getInstance] to create a new instance
         * next time it's called.
         */
        fun destroyInstance() {
            INSTANCE = null
        }
    }

    fun getTodayReadingTime(callback: GraphDataSource.LoadTaskCallback) {

        val todayDate = TimeUtils.getHmsClearDate(Date()) ?: return

        ReadingTimeDatabase.selectReadingTimeDataByDate(todayDate)?.let {
            callback.onGraphLoaded(it)
        } ?: let {
            callback.onDataNotAvailable()
        }
    }

    fun getReadingTimeByDate(date: Date, callback: GraphDataSource.LoadTaskCallback) {

        ReadingTimeDatabase.selectReadingTimeDataByDate(date)?.let {
            callback.onGraphLoaded(it)
        } ?: let {
            callback.onDataNotAvailable()
        }
    }

    fun getBetweenReadingTime(
        dayAgo: Int,
        callback: GraphDataSource.LoadTaskListCallback
    ) {
        val todayDate = TimeUtils.getHmsClearDate(Date()) ?: return
        val dayAgoDate = TimeUtils.getHmsClearDate(TimeUtils.getAdditionDate(dayAgo)) ?: return

        ReadingTimeDatabase.selectReadingTimeDataBetweenDate(dayAgoDate, todayDate)?.let {
            callback.onGraphListLoaded(it)
        } ?: let {
            callback.onDataNotAvailable()
        }
    }

    fun setTodayReadingTime(readingTimeData: ReadingTimeData) {
        ReadingTimeDatabase.deleteReadingTimeDataByDate(readingTimeData.date)
        ReadingTimeDatabase.insertReadingTimeData(readingTimeData)
    }

    fun setDemoReadingTime(readingTimeDataList: List<ReadingTimeData>) {
        for (readingTimeData in readingTimeDataList) {
            getReadingTimeByDate(readingTimeData.date, object : GraphDataSource.LoadTaskCallback {
                override fun onGraphLoaded(readingTimeData: ReadingTimeData) {
                    //no-op
                }

                override fun onDataNotAvailable() {
                    ReadingTimeDatabase.insertReadingTimeData(readingTimeData)
                }
            })
        }
    }
}