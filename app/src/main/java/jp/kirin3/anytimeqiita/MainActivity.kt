package jp.kirin3.anytimeqiita

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import io.realm.Realm
import jp.kirin3.anytimeqiita.model.LoginModel
import jp.kirin3.anytimeqiita.ui.folders.FoldersFragment
import jp.kirin3.anytimeqiita.ui.reading.ReadingFragment
import jp.kirin3.anytimeqiita.ui.record.RecordFragment
import jp.kirin3.anytimeqiita.ui.setting.SettingFragment
import jp.kirin3.anytimeqiita.ui.stocks.StocksFragment
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
//        val bottomNavigationView: BottomNavigationView =
//            findViewById(R.id.activity_main_bottom_navigation_view)


        activity_main_bottom_navigation_view.performClick()
        activity_main_bottom_navigation_view.setOnNavigationItemSelectedListener { item ->
            // フラグメントの新規作成
            addFragment(item.itemId)

            listFragment()
            return@setOnNavigationItemSelectedListener true
        }


//        val navController = findNavController(R.id.activity_main_navigation_host_fragment)
//
//        //BottomNavigationにNavigationを設定
//        bottomNavigationView.setupWithNavController(navController)

        // Qiitaログインページからの戻ってきた時の処理
        if (LoginModel.hasLoginParamInIntent(intent)) {
            LoginModel.handleQiitaLoginParam(intent, this)
            // Qiitaログインページからの戻りとして設定画面を開く
            transitSettingWithComeBackFromQiitaLogin(navController)
        } else if (!SettingsUtils.getWebViewUrl(this).isNullOrEmpty()) {
            // リーディングページがある場合のトップページ
//            navController.navigate(R.id.bottom_navigation_reading, null)
        } else {
            // 標準トップページ
//            navController.navigate(R.id.bottom_navigation_setting, null)

        }
    }

    private fun getTag(menuItemId: Int): String {
        when (menuItemId) {
            R.id.bottom_navigation_setting -> {
                return "setting"
            }
            R.id.bottom_navigation_reading -> {
                return "reading"
            }
            R.id.bottom_navigation_folder -> {
                return "folders"
            }
            R.id.bottom_navigation_stocks -> {
                return "stocks"
            }
            R.id.bottom_navigation_record -> {
                return "record"
            }
            else -> {
                return ""
            }
        }
    }

    private fun createFragment(menuItemId: Int): Fragment? {
        var selectedFragment: Fragment?
        when (menuItemId) {
            R.id.bottom_navigation_setting -> {
                selectedFragment = SettingFragment.newInstance()
            }
            R.id.bottom_navigation_reading -> {
                selectedFragment = ReadingFragment.newInstance()
            }
            R.id.bottom_navigation_folder -> {
                selectedFragment = FoldersFragment.newInstance()
            }
            R.id.bottom_navigation_stocks -> {
                selectedFragment = StocksFragment.newInstance()
            }
            R.id.bottom_navigation_record -> {
                selectedFragment = RecordFragment.newInstance()
            }
            else -> {
                selectedFragment = null
            }
        }

        return selectedFragment
    }

    private fun getFragmentFromMng(tag: String): Fragment? {
        val fragments = supportFragmentManager.fragments

        for (fragment in fragments) {
            if (fragment.tag.equals(tag)) {
                return fragment
            }
        }
        return null
    }

    public fun addFragment(menuItemId: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        val fragments = supportFragmentManager.fragments
        // addされているフラグメントを全て隠す
        for (hideFragment in fragments) {
            if (hideFragment.tag == null) {
                continue
            }
            transaction.hide(hideFragment)
        }
        // FragmentManagerから表示したいadd済みフラグメント取得
        var fragment = getFragmentFromMng(getTag(menuItemId))

        // 未addだったら表示したいフラグメントをadd
        if (fragment == null) {
            // フラグメントのインスタンスを取得
            fragment = createFragment(menuItemId) ?: return
            transaction.add(
                R.id.activity_main_navigation_host_fragment,
                fragment,
                getTag(menuItemId)
            )
        }
        // 表示したいフラグメントをshow
        transaction.show(fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun listFragment() {
        val fragments = supportFragmentManager.fragments

        for (fragment in fragments) {
            LOGI("taggg " + fragment.tag)
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
