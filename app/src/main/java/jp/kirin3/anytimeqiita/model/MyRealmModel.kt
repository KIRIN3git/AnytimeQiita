package jp.kirin3.anytimeqiita.model

import android.content.Context
import androidx.lifecycle.ViewModel
import io.realm.Realm
import io.realm.RealmConfiguration

class MyRealmModel : ViewModel() {

    companion object {

        private const val realmName = "qiita.realm"
        private var realmMyConfig: RealmConfiguration? = null


        // Realmセットアップ
        fun initRealm(context: Context) {
            Realm.init(context)
            // インスタンスの生成
            Realm.setDefaultConfiguration(getRealmConfig())
        }

        // Realm初期化
        fun resetRealm(context: Context) {
            Realm.init(context)
            Realm.deleteRealm(getRealmConfig())
            // インスタンスの生成
            Realm.setDefaultConfiguration(getRealmConfig())
        }

        private fun getRealmConfig(): RealmConfiguration {

            realmMyConfig?.let {
                return it
            }

            val realmConfig = RealmConfiguration
                .Builder()
//            .schemaVersion(2)
//            .migration(MyMigration())
                .name(realmName).build()

            realmMyConfig = realmConfig
            return realmConfig
        }

    }
}