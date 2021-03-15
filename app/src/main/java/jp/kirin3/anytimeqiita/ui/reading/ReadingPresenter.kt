package jp.kirin3.anytimeqiita.ui.reading

import jp.kirin3.anytimeqiita.data.ReadingTimeData
import jp.kirin3.anytimeqiita.ui.stocks.GraphDataSource
import jp.kirin3.anytimeqiita.ui.stocks.GraphRepository
import kirin3.jp.mljanken.util.TimeUtils
import java.io.BufferedReader
import java.io.File
import java.util.*

class ReadingPresenter(
    private val graphRepository: GraphRepository,
    private val readingView: ReadingContract.View
) : ReadingContract.Presenter {

    private lateinit var viewModel: ReadingViewModel

    init {
        readingView.presenter = this
    }

    override fun setup(viewModel: ReadingViewModel) {
        this.viewModel = viewModel
    }

    fun addReadingTimeToDb(readingTime: Int) {
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

    override fun setRandomDemoReadingTime() {
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

//    override fun hasReadingFile(dir: String, file: String): Boolean {
//        return getReadingFile(dir, file) != null
//    }
//
//    private fun getReadingFile(dir: String, file: String): String? {
//        val readFile = File(dir, file)
//
//        return if (!readFile.exists()) {
//            null
//        } else {
//            readFile.bufferedReader().use(BufferedReader::readText)
//        }
//    }

    override fun setStartTime() {
        viewModel.startTime = System.currentTimeMillis()
    }

    override fun setEndTime() {
        viewModel.endTime = System.currentTimeMillis()
    }

    override fun setReadingTime() {
        val readingTime = getReadingTime()
        addReadingTimeToDb(readingTime)
    }

    private fun getReadingTime(): Int {
        if (viewModel.startTime == 0L || viewModel.endTime == 0L) return 0
        return ((viewModel.endTime - viewModel.startTime) / (60L * 1000L)).toInt()
    }
}

