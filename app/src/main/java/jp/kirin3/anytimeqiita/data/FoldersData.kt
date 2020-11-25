package jp.kirin3.anytimeqiita.data

import android.os.Parcelable
import io.realm.RealmObject
import io.realm.annotations.Index
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
open class FoldersData(
    @Index var seqid: Int = 0,
    var name: String = "",
    var created_at: Date = Date()
) : RealmObject(), Parcelable

