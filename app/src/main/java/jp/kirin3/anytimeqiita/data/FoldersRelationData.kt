package jp.kirin3.anytimeqiita.data

import io.realm.RealmObject
import io.realm.annotations.Index
import java.util.*

open class FoldersRelationData(
    @Index var seqid: Int = 0,
    var folders_id: String = "",
    var stocks_id: String = ""
) : RealmObject()

