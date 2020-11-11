package kirin3.jp.mljanken.util

import android.content.Context
import android.text.format.DateUtils
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
        }

        return null
    }

    /**
     * Date型 → String変換(YYYY/MM/DD)
     */
    fun formatShortDate(context: Context, date: Date): String {
        val format = android.text.format.DateFormat.getMediumDateFormat(context)
        return format.format(date).toLowerCase(Locale.JAPAN)
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
}
