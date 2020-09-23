package jp.kirin3.anytimeqiita.database

import io.realm.Realm
import io.realm.kotlin.where
import jp.kirin3.anytimeqiita.data.FilesData

object FilesDatabase {

    fun insertFailsDataList(relationList: List<FilesData>?) {
        if (relationList == null) return

        var realm = Realm.getDefaultInstance()

        realm.beginTransaction()
        for (folder in relationList) {
            realm.insert(folder)
        }
        realm.commitTransaction()

        realm.close()
    }

    fun deleteFailsDataList() {
        var realm = Realm.getDefaultInstance()

        val relationData = realm.where<FilesData>().findAll()

        realm.beginTransaction()
        relationData.deleteAllFromRealm()
        realm.commitTransaction()

        realm.close()

    }

    fun selectFailsData(): List<FilesData>? {
        var realm = Realm.getDefaultInstance()

        val relation = realm.where<FilesData>().findAll()
        if (relation.count() == 0) return null
        val relationList = realm.copyFromRealm(relation)

        realm.close()

        return relationList

    }
}