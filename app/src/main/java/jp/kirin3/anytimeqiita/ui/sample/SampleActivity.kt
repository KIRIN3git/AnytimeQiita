package jp.kirin3.anytimeqiita.ui.sample

import android.os.Bundle
import jp.kirin3.anytimeqiita.BaseActivity
import jp.kirin3.anytimeqiita.R
import kirin3.jp.mljanken.util.LogUtils.LOGI

class SampleActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LOGI("")
        setContentView(R.layout.activity_sample)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, SampleFragment.newInstance())
            .commit()

    }
}
