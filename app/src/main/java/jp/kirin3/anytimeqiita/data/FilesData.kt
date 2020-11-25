package jp.kirin3.anytimeqiita.data

import android.os.Parcelable
import io.realm.RealmObject
import kotlinx.android.parcel.Parcelize

/**
 * ファイルデータ
 * フォルダ内のデータとしてストックデータと結びつく
 */
@Parcelize
open class FilesData(
    var folders_seqid: Int? = null,
    var stocks_id: String? = null
) : RealmObject(), Parcelable
