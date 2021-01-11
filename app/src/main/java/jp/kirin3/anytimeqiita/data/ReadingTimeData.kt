package jp.kirin3.anytimeqiita.data

import android.os.Parcelable
import io.realm.RealmObject
import io.realm.annotations.Index
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
open class ReadingTimeData(
    @Index var date: Date = Date(),
    var minute: Int = 0
) : RealmObject(), Parcelable

