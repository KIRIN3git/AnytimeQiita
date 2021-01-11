package jp.kirin3.anytimeqiita.ui.reading

import jp.kirin3.anytimeqiita.data.ReadingTimeData
import jp.kirin3.anytimeqiita.ui.stocks.GraphDataSource
import jp.kirin3.anytimeqiita.ui.stocks.GraphRepository
import kirin3.jp.mljanken.util.TimeUtils
import java.util.*

class ReadingPresenter(
    private val graphRepository: GraphRepository,
    private val readingView: ReadingContract.View
) : ReadingContract.Presenter {

    init {
        readingView.presenter = this
    }

    override fun addReadingTimeToDb(readingTime: Int) {
        graphRepository.getTodayReadingTime(object : GraphDataSource.LoadTaskCallback {
            override fun onGraphLoaded(readingTimeData: ReadingTimeData) {
                val inputData =
                    ReadingTimeData(readingTimeData.date, readingTimeData.minute + readingTime)
                graphRepository.setTodayReadingTime(inputData)
            }

            override fun onDataNotAvailable() {
                val today = TimeUtils.getHmsClearDate(Date()) ?: return
                val inputData =
                    ReadingTimeData(today, readingTime)
                TimeUtils.getHmsClearDate(Date())
                graphRepository.setTodayReadingTime(inputData)
            }
        })
    }

    override fun setRandamDemoReadingTime() {
        val randomDataNum = 400
        val randomMinute = 300
        val readingTimeList = mutableListOf<ReadingTimeData>()
        for (i in 0 until randomDataNum) {
            val date = TimeUtils.getHmsClearDate(TimeUtils.getAdditionDate(-i)) ?: continue
            readingTimeList.add(
                ReadingTimeData(date, Random().nextInt(randomMinute))
            )
        }
        graphRepository.setDemoReadingTime(readingTimeList)
    }
}

