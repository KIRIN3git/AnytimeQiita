package jp.kirin3.anytimeqiita

import android.app.Application
import jp.kirin3.anytimeqiita.di.AppComponent
import jp.kirin3.anytimeqiita.di.AppModule
import jp.kirin3.anytimeqiita.di.DaggerAppComponent
import jp.kirin3.anytimeqiita.model.MyRealmModel
import jp.kirin3.anytimeqiita.util.log.ExtDebugTree
import timber.log.Timber


class MainApplication : Application() {

    companion object {
        const val LOG_TAG = "KIRIN3_LOG"
        const val QIITA_CLIENT_ID = "2d2713c9fb8be9972a134670392dc4df46388034"
        const val QIITA_CLIENT_SEACRET = "972fe1788f23c93aa8546d1b99ab1c0677596f53"

        lateinit var component: AppComponent private set
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(ExtDebugTree(LOG_TAG))
        }

        MyRealmModel.initRealm(this)

        // Dagger2セットアップ
        component = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}