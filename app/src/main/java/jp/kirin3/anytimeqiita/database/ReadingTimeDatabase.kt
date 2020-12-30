package jp.kirin3.anytimeqiita.database

import io.realm.Realm
import io.realm.kotlin.where
import jp.kirin3.anytimeqiita.data.ReadingTimeData
import java.util.*

object ReadingTimeDatabase {

    private const val DATE = "date"
    fun insertReadingTimeData(readingTimeData: ReadingTimeData?) {
        if (readingTimeData == null) return

        var realm = Realm.getDefaultInstance()

        realm.beginTransaction()
        realm.insert(readingTimeData)
        realm.commitTransaction()

        realm.close()
    }

    fun deleteReadingTimeDataByDate(date: Date) {

        var realm = Realm.getDefaultInstance()

        val readingTimeData = realm.where<ReadingTimeData>()
            .equalTo(DATE, date)
            .findAll()

        realm.beginTransaction()
        readingTimeData.deleteAllFromRealm()
        realm.commitTransaction()

        realm.close()
    }

    fun selectReadingTimeData(): List<ReadingTimeData>? {

        var realm = Realm.getDefaultInstance()

        val readingTimeDataResult = realm.where<ReadingTimeData>().findAll()
        if (readingTimeDataResult.count() == 0) return null
        val readingTimeDataList = realm.copyFromRealm(readingTimeDataResult)

        realm.close()

        return readingTimeDataList
    }

    fun selectReadingTimeDataByDate(date: Date): ReadingTimeData? {

        var realm = Realm.getDefaultInstance()

        val readingTimeDataResult = realm.where<ReadingTimeData>().equalTo(DATE, date).findAll()
        if (readingTimeDataResult.count() == 0) return null
        val readingTimeDataList = realm.copyFromRealm(readingTimeDataResult)

        realm.close()

        return readingTimeDataList[0]
    }
}