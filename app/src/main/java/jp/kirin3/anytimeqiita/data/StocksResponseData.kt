package jp.kirin3.anytimeqiita.data

import android.os.Parcelable
import io.realm.RealmObject
import io.realm.annotations.Index
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
open class StocksResponseData(
    @Index var sequence: Int = 0,
    var id: String = "",
    var coediting: Boolean = false,
    var user: UserData? = UserData(),
    var title: String = "",
    var url: String = "",
    var likes_count: Int = 0,
    var updated_at: Date = Date()
) : RealmObject(), Parcelable

//data class GroupData(
//    val created_at: String = "",
//    val id: Int = 0,
//    val name: String = "",
//    val private: Boolean = false
//)

@Parcelize
open class UserData(
    var id: String = "",
    var profile_image_url: String = ""
) : RealmObject(), Parcelable


