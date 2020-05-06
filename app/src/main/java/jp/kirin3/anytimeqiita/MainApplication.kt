package jp.kirin3.anytimeqiita

import android.app.Application
import jp.kirin3.anytimeqiita.util.log.ExtDebugTree
import timber.log.Timber

class MainApplication : Application() {

    companion object {
        const val LOG_TAG = "KIRIN3_LOG"
        const val QIITA_CLIENT_ID = "XXXXXXXXXXXXXXXXX"
        const val QIITA_CLIENT_SEACRET = "YYYYYYYYYYYYYYYYYY"

    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(ExtDebugTree(LOG_TAG))
        }
    }
}