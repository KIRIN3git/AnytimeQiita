package jp.kirin3.anytimeqiita

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import jp.kirin3.anytimeqiita.helper.AccessTokenHelper
import jp.kirin3.anytimeqiita.helper.AuthenticatedUserHelper
import jp.kirin3.anytimeqiita.helper.LoginHelper
import kirin3.jp.mljanken.util.LogUtils.LOGI
import kirin3.jp.mljanken.util.SettingsUtils
import java.util.concurrent.CountDownLatch

class MainActivity : BaseActivity() {

    companion object {
        const val EXTRA_TASK_ID = "TASK_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LOGI("")

        setContentView(R.layout.activity_main)
        val bottomNavigationView: BottomNavigationView =
            findViewById(R.id.activity_main_bottom_navigation_view)
        val navController = findNavController(R.id.activity_main_navigation_host_fragment)

        /*
        // TODO: KIRIN3 ActionBar設置時に指定
        // 各メニューIDを一連のIDとして渡します
        // メニューは最上位の宛先と見なされる必要があります。

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.bottom_navigation_home, R.id.bottom_navigation_dashboard, R.id.bottom_navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        */

        // createBookmarkPresenter()

        LOGI("MAIN")
//        val pd = ProgressDialog(this)
//        pd.setMessage("読み込み中")
//        pd.show()
        processAfterLogin(intent)


        //BottomNavigatinにNavigationを設定
        bottomNavigationView.setupWithNavController(navController)
    }

    fun showLoginToast() {
        Toast.makeText(
            this,
            this.getString(R.string.login_success),
            Toast.LENGTH_LONG
        ).show()
    }

    fun FragmentManager.getCurrentNavigationFragment(): Fragment? =
        primaryNavigationFragment?.childFragmentManager?.fragments?.first()

    fun processAfterLogin(intent: Intent) {

        var latch = CountDownLatch(1);
        if (!LoginHelper.setLoginParamToPrefarence(this, intent)) return

        AccessTokenHelper.loadAccessToken(this, LoginHelper.getQiitaCode(this), latch)
        latch.await()

        latch = CountDownLatch(1)
        AuthenticatedUserHelper.loadAuthenticatedUser(
            this,
            SettingsUtils.getQiitaAccessToken(this),
            latch
        )
        latch.await()

        if(AuthenticatedUserHelper.hasAuthenticatedUser()){
            LOGI("xxxxxxxxxxxxxxxx " + AuthenticatedUserHelper.authenticatedModel.getAuthenticatedUser()?.id)
            showLoginToast()
        }
    }
}
