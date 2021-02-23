package jp.kirin3.anytimeqiita

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.realm.Realm
import jp.kirin3.anytimeqiita.ui.reading.LoginModel
import jp.kirin3.anytimeqiita.ui.setting.SettingFragment
import kirin3.jp.mljanken.util.LogUtils.LOGI

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
        //BottomNavigatinにNavigationを設定
        bottomNavigationView.setupWithNavController(navController)

        var isLoginMode = false

        // Qiitaログインページからの戻ってきた時の処理
        if (LoginModel.hasLoginParamInPreference(intent)) {
            LoginModel.setQiitaLoginCode(intent, this)
            isLoginMode = true
        }

        // セッティング画面に遷移
        // 標準がセッティング画面だが、パラメータを渡すので明示的にしている
        val params = bundleOf(
            SettingFragment.IS_LOGIN_MODE to isLoginMode
        )
        navController.navigate(R.id.bottom_navigation_setting, params)
    }


    fun FragmentManager.getCurrentNavigationFragment(): Fragment? =
        primaryNavigationFragment?.childFragmentManager?.fragments?.first()


    override fun onDestroy() {
        super.onDestroy()

    }


}
