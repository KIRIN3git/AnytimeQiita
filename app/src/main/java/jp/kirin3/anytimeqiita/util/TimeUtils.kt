package kirin3.jp.mljanken.util

import android.content.Context
import android.text.format.DateUtils
import kirin3.jp.mljanken.util.LogUtils.LOGE
import kirin3.jp.mljanken.util.LogUtils.LOGI
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object TimeUtils {

    private val DAY_FLAGS =
        DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_NO_YEAR or DateUtils.FORMAT_SHOW_WEEKDAY or DateUtils.FORMAT_ABBREV_WEEKDAY

    /**
     * 現在時刻ミリ秒を取得
     */
    fun getCurrentTime(context: Context): Long {
        return System.currentTimeMillis()
    }

    /**
     * 現在時刻String(YYYY/MM/DD)
     */
    fun getFormatShortDate(context: Context): String {
        val date = Date()
        val format = android.text.format.DateFormat.getMediumDateFormat(context)
        return format.format(date).toLowerCase(Locale.JAPAN)
    }

    /**
     * String型("yyyy-MM-dd kk:mm:ss") → Date型に変換
     */
    fun parseTimestamp(timestamp: String): Date? {
        val format = SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.JAPAN)
        try {
            return format.parse(timestamp)
        } catch (ex: ParseException) {
            LOGE("ParseException")
        }

        return null
    }


    /**
     * Date型 → String変換(MM/DD)
     */
    fun getStringMmddFromDate(date: Date): String {
        val df = SimpleDateFormat("MM/dd")
        return df.format(date)
    }

    /**
     * Date型 → String変換(MM/DD)
     */
    fun getStringYymmFromDate(date: Date): String {
        val df = SimpleDateFormat("YYYY/MM")
        return df.format(date)
    }

    /**
     * Date型 → String変換(MM/DD)
     */
    fun getStringYymmddFromDate(date: Date): String {
        val df = SimpleDateFormat("YYYY/MM/DD")
        return df.format(date)
    }

    /**
     * Date型 → String変換(午前or午後HH:MM)
     * 午前10:58
     */
    fun formatShortTime(context: Context, time: Date): String {
        val format = android.text.format.DateFormat.getTimeFormat(context)
        return format.format(time).toLowerCase(Locale.JAPAN)
    }

    /**
     * long型 → String型変換
     * 2018年7月4日(水)
     */
    fun formatDateTime(context: Context, time: Long): String {
        return DateUtils.formatDateTime(
            context,
            time,
            DateUtils.FORMAT_SHOW_YEAR or DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_WEEKDAY or DateUtils.FORMAT_SHOW_TIME or DateUtils.FORMAT_NUMERIC_DATE or DateUtils.FORMAT_ABBREV_ALL
        )
    }

    /**
     * long型 → String型変換
     * 7月4日(水)
     */
    fun formatDaySeparator(context: Context, time: Long): String {
        val recycle = StringBuilder()
        val formatter = Formatter(recycle)
        return DateUtils.formatDateRange(context, formatter, time, time, DAY_FLAGS).toString()
    }

    /**
     * Date型の時分秒を0クリア
     */
    fun getHmsClearDate(date: Date): Date? {
        val fmt = SimpleDateFormat("yyyy-MM-dd 00:00:00")
        return parseTimestamp(fmt.format(date))
    }

    /**
     * Date型を指定日数分ずらして取得
     */
    fun getAdditionDate(addDay: Int): Date {
        val date = Date()
        val calendar = Calendar.getInstance()
        calendar.time = date

        calendar.add(Calendar.DAY_OF_MONTH, addDay)
        return calendar.time
    }

    /**
     * Date型の曜日を取得
     *
     * Calendar.SUNDAY:1
     * Calendar.MONDAY:2
     * Calendar.TUESDAY:3
     * Calendar.WEDNESDAY:4
     * Calendar.THURSDAY:5
     * Calendar.FRIDAY:6
     * Calendar.SATURDAY:7
     */
    fun getDayOfTheWeekFromDate(date: Date): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.get(Calendar.DAY_OF_WEEK)
    }

    /**
     * Dateから日付を取得
     */
    fun getCalendarDateFromDate(date: Date): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.get(Calendar.DATE)
    }

    /**
     * Dateから月を取得
     */
    fun getCalendarMonthFromDate(date: Date): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.get(Calendar.MONTH)
    }

    /**
     * Dateから月の日数を取得
     * addMonth:月をずらしたい場合に指定
     */
    fun getDayOfMonthFromDate(date: Date, addMonth: Int): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.MONTH, addMonth)
        val gregorianCalendar: Calendar =
            GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH + 1), 1)

        return gregorianCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    }
}
