package jp.kirin3.anytimeqiita

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration
import jp.kirin3.anytimeqiita.util.log.ExtDebugTree
import timber.log.Timber


class MainApplication : Application() {

    companion object {
        const val LOG_TAG = "KIRIN3_LOG"
        const val QIITA_CLIENT_ID = "2d2713c9fb8be9972a134670392dc4df46388034"
        const val QIITA_CLIENT_SEACRET = "972fe1788f23c93aa8546d1b99ab1c0677596f53"

    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(ExtDebugTree(LOG_TAG))
        }

        Realm.init(this)
        val config = RealmConfiguration
            .Builder()
//            .schemaVersion(2)
//            .migration(MyMigration())
            .name("myrealm.realm").build()
        // インスタンスの生成
        Realm.setDefaultConfiguration(config)
    }
}