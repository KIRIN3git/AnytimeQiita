package jp.kirin3.anytimeqiita.data

import io.realm.RealmObject
import java.util.*

open class StocksResponseData(
    var rendered_body: String = "",
    var body: String = "",
    var coediting: Boolean = false,
    var user: UserData? = UserData(),
    var title: String = "",
    var url: String = "",
    var likes_count: Int = 0,
    var created_at: Date = Date(),
    var updated_at: Date = Date()
) : RealmObject()

//data class GroupData(
//    val created_at: String = "",
//    val id: Int = 0,
//    val name: String = "",
//    val private: Boolean = false
//)

open class UserData(
    var followees_count: Int = 0,
    var id: String = "",
    var profile_image_url: String = ""
) : RealmObject()


