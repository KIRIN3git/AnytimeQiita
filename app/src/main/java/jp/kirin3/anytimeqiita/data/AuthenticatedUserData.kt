package jp.kirin3.anytimeqiita.data

import io.realm.RealmObject
import io.realm.annotations.Required

open class AuthenticatedUserData(
    @Required var id: String = "",
    var itemsCount: Int = 0
) : RealmObject()
