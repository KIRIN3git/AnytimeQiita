package kirin3.jp.mljanken.util

import android.content.Context
import java.io.*
import java.net.URLEncoder
import java.nio.charset.Charset


object IOUtils {

    val CHARSET_UTF8 = Charset.forName("UTF-8")

    val FILE_BOOTSTRAP_DATA = "bootstrap_data"

    /*
     * ファイルへの書き込み
     */
    fun writeToFile(context: Context, data: String, filename: String) {
        writeToFile(context, data.toByteArray(CHARSET_UTF8), filename)
    }

    fun writeToFile(context: Context, data: ByteArray, filename: String) {
        val file = File(context.filesDir, filename)
        try {
            val os = FileOutputStream(file)
            os.write(data)
            os.flush()
            // Perform an fsync on the FileOutputStream.
            os.fd.sync()
            os.close()
        } catch (e: FileNotFoundException) {
        } catch (e: SyncFailedException) {
        } catch (e: IOException) {
        }

    }

    /*
     * ファイルからの読み込み
     */
    fun readAsString(context: Context, filename: String): String {
        val file = File(context.filesDir, filename)
        val sb = StringBuilder()
        try {
            val fis = FileInputStream(file)
            val reader = BufferedReader(InputStreamReader(fis, CHARSET_UTF8))

            var line: String?
            do {
                line = reader.readLine()
                if (line == null) break
                sb.append(line)
            } while (true)

            reader.close()
        } catch (e: FileNotFoundException) {
        } catch (e: IOException) {
        }

        return sb.toString()
    }

    /*
     * 文字列のエンコード
     */
    fun encode(text: String): String {
        var encodedString = text
        try {
            encodedString = URLEncoder.encode(encodedString, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            encodedString = text
        }

        return encodedString
    }
}
