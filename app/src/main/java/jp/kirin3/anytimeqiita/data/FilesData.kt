package jp.kirin3.anytimeqiita.data

import io.realm.RealmObject

/**
 * ファイルデータ
 * フォルダ内のデータとしてストックデータと結びつく
 */
open class FilesData(
    var folders_seqid: Int = -1,
    var stocks_id: String? = null
) : RealmObject()
