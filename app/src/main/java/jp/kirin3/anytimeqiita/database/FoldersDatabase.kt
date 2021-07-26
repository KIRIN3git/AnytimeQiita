package jp.kirin3.anytimeqiita.database

import io.realm.Realm
import io.realm.kotlin.where
import jp.kirin3.anytimeqiita.data.FoldersData

object FoldersDatabase {

    private const val SEQID = "seqid"
    fun insertFoldersDataList(foldersList: List<FoldersData>?) {
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

        val nowUserData = realm.where<FoldersData>().findAll()

        realm.beginTransaction()
        nowUserData.deleteAllFromRealm()
        realm.commitTransaction()

        realm.close()
    }

    fun deleteFoldersDataListBySeqid(seqid: Int) {

        var realm = Realm.getDefaultInstance()

        val userData = realm.where<FoldersData>()
            .equalTo(SEQID, seqid)
            .findAll()

        realm.beginTransaction()
        userData.deleteAllFromRealm()
        realm.commitTransaction()

        realm.close()
    }

    fun selectFoldersData(): List<FoldersData>? {

        var realm = Realm.getDefaultInstance()

        val folders = realm.where<FoldersData>().findAll()
        if (folders.count() == 0){
            realm.close()
            return null
        }
        val foldersList = realm.copyFromRealm(folders)

        realm.close()

        return foldersList

    }
}