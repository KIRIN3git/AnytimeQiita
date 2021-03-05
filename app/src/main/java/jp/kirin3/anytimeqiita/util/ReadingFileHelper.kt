package jp.kirin3.anytimeqiita.util

import android.content.Context
import java.io.BufferedReader
import java.io.File

class ReadingFileHelper {


    companion object {
        private const val READER_FILE_MHT = "reader.mht"

        fun hasReadingFile(context: Context?): Boolean {
            if (context == null) return false
            return getReadingFile(context?.getExternalFilesDir(null).toString()) != null
        }

        private fun getReadingFile(dir: String): String? {
            val readFile = File(dir, READER_FILE_MHT)

            return if (!readFile.exists()) {
                null
            } else {
                readFile.bufferedReader().use(BufferedReader::readText)
            }
        }

        fun getReadingFileFullPath(context: Context?): String? {
            if (context == null) return null
            return context?.getExternalFilesDir(null).toString() + "/" + READER_FILE_MHT
        }
    }
}