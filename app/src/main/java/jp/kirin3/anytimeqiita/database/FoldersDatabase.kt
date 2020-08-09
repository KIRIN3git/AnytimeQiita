package jp.kirin3.anytimeqiita.database

import io.realm.Realm
import io.realm.kotlin.where
import jp.kirin3.anytimeqiita.data.FoldersBasicData

object FoldersDatabase {

    fun insertFoldersDataList(foldersList: List<FoldersBasicData>?) {
        if (foldersList == null) return

        var realm = Realm.getDefaultInstance()

        realm.beginTransaction()
        for (folder in foldersList) {
            realm.insert(folder)
        }
        realm.commitTransaction()

        realm.close()
    }

    fun deleteFoldersDataList() {

        var realm = Realm.getDefaultInstance()

        val nowUserData = realm.where<FoldersBasicData>().findAll()

        realm.beginTransaction()
        nowUserData.deleteAllFromRealm()
        realm.commitTransaction()

        realm.close()

    }

    fun selectFoldersData(): List<FoldersBasicData>? {

        var realm = Realm.getDefaultInstance()

        val folders = realm.where<FoldersBasicData>().findAll()
        if (folders.count() == 0) return null
        val foldersList = realm.copyFromRealm(folders)

        realm.close()

        return foldersList

    }
}