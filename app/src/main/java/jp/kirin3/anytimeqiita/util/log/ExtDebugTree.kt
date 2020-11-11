package jp.kirin3.anytimeqiita.util.log

import android.annotation.SuppressLint
import android.text.TextUtils
import android.util.Log
import timber.log.Timber.DebugTree


class ExtDebugTree(tag: String) : DebugTree() {

    private val showLink = true
    private var callerInfo: String? = null
    private val default_tag: String = tag

    companion object {

        private const val MAX_LOG_LENGTH = 4000
        private const val CALLER_INFO_FORMAT = " [%s](%s:%s)"

        private fun formatForLogCat(stack: StackTraceElement): String {
            val className = stack.className
            val packageName = className.substring(0, className.lastIndexOf("."))
            /**+
             * ログのフォーマット変更
             */
            return String.format(
//                CALLER_INFO_FORMAT, packageName + "[" + stack.methodName + "]" ,
                CALLER_INFO_FORMAT, stack.methodName,
                stack.fileName, stack.lineNumber
            )
        }
    }

    private fun setTagByForce(tag: String?): String {
        return default_tag
    }

    override fun log(
        priority: Int,
        tag: String?,
        message: String,
        t: Throwable?
    ) {
        if (showLink) {
            callerInfo = getCallerInfo(Throwable().stackTrace)
        }
        if (message.length < MAX_LOG_LENGTH) {
            printSingleLine(priority, setTagByForce(tag), message + callerInfo)
        } else {
            printMultipleLines(priority, setTagByForce(tag), message)
        }
    }

    private fun printMultipleLines(
        priority: Int,
        tag: String?,
        message: String
    ) { // Split by line, then ensure each line can fit into Log's maximum length.
        var i = 0
        val length = message.length
        while (i < length) {
            var newline = message.indexOf('\n', i)
            newline = if (newline != -1) newline else length
            do {
                val end =
                    Math.min(newline, i + MAX_LOG_LENGTH)
                val part = message.substring(i, end)
                printSingleLine(priority, tag, part)
                i = end
            } while (i < newline)
            i++
        }
        if (showLink && !TextUtils.isEmpty(callerInfo)) {
            printSingleLine(priority, tag, callerInfo)
        }
    }

    @SuppressLint("LogNotTimber")
    private fun printSingleLine(
        priority: Int,
        tag: String?,
        message: String?
    ) {
        if (priority == Log.ASSERT) {
            Log.wtf(tag, message)
        } else {
            Log.println(priority, tag, message)
        }
    }

    /***
     * ログに表示するファイル位置
     *　Timberの箇所:5　一つ前のフォルダ:6
     */
    private fun getCallerInfo(stacks: Array<StackTraceElement>?): String {
        return if (stacks == null || stacks.size < 6) { // are you using proguard???
            ""
        } else formatForLogCat(
            stacks[6]
        )
    }
}