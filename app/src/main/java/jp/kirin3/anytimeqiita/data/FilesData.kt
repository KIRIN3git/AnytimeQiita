package jp.kirin3.anytimeqiita.data

import io.realm.RealmObject
import io.realm.annotations.Index
import java.util.*

/**
 * ファイルデータ
 * フォルダ内のデータとしてストックデータと結びつく
 */
open class FilesData(
    var folders_id: Int = 0,
    var stocks_id: Int = 0
) : RealmObject()

