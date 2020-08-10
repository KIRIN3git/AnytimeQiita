package jp.kirin3.anytimeqiita.database

import io.realm.Realm
import io.realm.kotlin.where
import jp.kirin3.anytimeqiita.data.RelationData

object RelationDatabase {

    fun insertRelationDataList(relationList: List<RelationData>?) {
        if (relationList == null) return

        var realm = Realm.getDefaultInstance()

        realm.beginTransaction()
        for (folder in relationList) {
            realm.insert(folder)
        }
        realm.commitTransaction()

        realm.close()
    }

    fun deleteRelationDataList() {
        var realm = Realm.getDefaultInstance()

        val relationData = realm.where<RelationData>().findAll()

        realm.beginTransaction()
        relationData.deleteAllFromRealm()
        realm.commitTransaction()

        realm.close()

    }

    fun selectRelationData(): List<RelationData>? {
        var realm = Realm.getDefaultInstance()

        val relation = realm.where<RelationData>().findAll()
        if (relation.count() == 0) return null
        val relationList = realm.copyFromRealm(relation)

        realm.close()

        return relationList

    }
}