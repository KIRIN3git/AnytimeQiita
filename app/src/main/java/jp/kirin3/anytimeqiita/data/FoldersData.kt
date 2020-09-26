package jp.kirin3.anytimeqiita.data

import io.realm.RealmObject
import io.realm.annotations.Index
import java.util.*

open class FoldersData(
    @Index var seqid: Int = 0,
    var name: String = "",
    var created_at: Date = Date(),
    var add_flg: Boolean = false
) : RealmObject()

