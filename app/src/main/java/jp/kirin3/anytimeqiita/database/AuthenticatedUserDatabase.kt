package jp.kirin3.anytimeqiita.database

import io.realm.Realm
import io.realm.kotlin.where
import jp.kirin3.anytimeqiita.data.AuthenticatedUserData

object AuthenticatedUserDatabase {

    fun insertAuthenticatedUserData(userData: AuthenticatedUserData) {
        if (userData == null) return

        var realm = Realm.getDefaultInstance()
        val nowUserData = realm.where<AuthenticatedUserData>().findAll()

        realm.beginTransaction()
        nowUserData.deleteAllFromRealm()
        realm.insert(userData)
        realm.commitTransaction()

        realm.close()
    }


    fun selectAuthenticatedUserData(): AuthenticatedUserData? {

        var realm = Realm.getDefaultInstance()

        val users = realm.where<AuthenticatedUserData>().findAll()
        if (users.count() == 0){
            realm.close()
            return null
        }
        val user = realm.copyFromRealm(users[0])

        realm.close()

        return user
    }


    fun deleteAuthenticatedUserData() {

        var realm = Realm.getDefaultInstance()

        val nowUserData = realm.where<AuthenticatedUserData>().findAll()

        realm.beginTransaction()
        nowUserData.deleteAllFromRealm()
        realm.commitTransaction()

        realm.close()

    }
}