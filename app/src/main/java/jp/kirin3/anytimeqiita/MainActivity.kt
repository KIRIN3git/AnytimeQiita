package jp.kirin3.anytimeqiita

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.realm.Realm
import jp.kirin3.anytimeqiita.model.LoginModel
import jp.kirin3.anytimeqiita.ui.setting.SettingFragment
import kirin3.jp.mljanken.util.LogUtils.LOGI
import kirin3.jp.mljanken.util.SettingsUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private lateinit var realm: Realm

    companion object {
        const val EXTRA_TASK_ID = "TASK_ID"
    }

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LOGI("")


        setContentView(R.layout.activity_main)
        val bottomNavigationView: BottomNavigationView =
            findViewById(R.id.activity_main_bottom_navigation_view)
        val navController = findNavController(R.id.activity_main_navigation_host_fragment)

        activity_main_navigation_host_fragment

        //BottomNavigatinにNavigationを設定
        bottomNavigationView.setupWithNavController(navController)

        // Qiitaログインページからの戻ってきた時の処理
        if (LoginModel.hasLoginParamInIntent(intent)) {
            LoginModel.handleQiitaLoginParam(intent, this)
            // Qiitaログインページからの戻りとして設定画面を開く
            transitSettingWithComeBackFromQiitaLogin(navController)
        } else if (!SettingsUtils.getWebViewUrl(this).isNullOrEmpty()) {
            // リーディングページがある場合のトップページ
            navController.navigate(R.id.bottom_navigation_reading, null)
        } else  {
            // 標準トップページ
            navController.navigate(R.id.bottom_navigation_setting, null)
        }
    }

    private fun transitSettingWithComeBackFromQiitaLogin(navController: NavController) {
        val params = bundleOf(
            SettingFragment.COME_BACK_FROM_QIITA_LOGIN to true
        )
        navController.navigate(R.id.bottom_navigation_setting, params)
    }


    fun FragmentManager.getCurrentNavigationFragment(): Fragment? =
        primaryNavigationFragment?.childFragmentManager?.fragments?.first()


    override fun onDestroy() {
        super.onDestroy()

    }


}
