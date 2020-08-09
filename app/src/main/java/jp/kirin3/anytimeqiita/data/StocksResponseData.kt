package jp.kirin3.anytimeqiita.data

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class StocksResponseData(
    @PrimaryKey var id: String = "",
    var coediting: Boolean = false,
    var user: UserData? = UserData(),
    var title: String = "",
    var url: String = "",
    var likes_count: Int = 0,
    var updated_at: Date = Date()
) : RealmObject()

//data class GroupData(
//    val created_at: String = "",
//    val id: Int = 0,
//    val name: String = "",
//    val private: Boolean = false
//)

open class UserData(
    var id: String = "",
    var profile_image_url: String = ""
) : RealmObject()


