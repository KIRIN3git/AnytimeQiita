package jp.kirin3.anytimeqiita.database

import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import jp.kirin3.anytimeqiita.data.AuthenticatedUserResponceData
import kirin3.jp.mljanken.util.LogUtils

object AuthenticatedUserDatabase {

    fun insertAuthenticatedUserData(userData: AuthenticatedUserResponceData) {
        if (userData == null) return

        var realm = Realm.getDefaultInstance()
        val nowUserData = realm.where<AuthenticatedUserResponceData>().findAll()

        realm.beginTransaction()
        nowUserData.deleteAllFromRealm()
        realm.insert(userData)
        realm.commitTransaction()

        realm.close()
    }

    fun deleteAuthenticatedUserData() {

        var realm = Realm.getDefaultInstance()

        val nowUserData = realm.where<AuthenticatedUserResponceData>().findAll()

        realm.beginTransaction()
        nowUserData.deleteAllFromRealm()
        realm.commitTransaction()

        realm.close()

    }

    fun selectAuthenticatedUserData(): AuthenticatedUserResponceData? {

        var realm = Realm.getDefaultInstance()

        val users = realm.where<AuthenticatedUserResponceData>().findAll()
        if(users.count() == 0) return null
        val user = realm.copyFromRealm(users[0])

        realm.close()

        return user

    }
}