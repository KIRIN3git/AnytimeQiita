package jp.kirin3.anytimeqiita

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import jp.kirin3.anytimeqiita.ui.login.LoginHelper

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavigationView: BottomNavigationView =
            findViewById(R.id.activity_main_bottom_navigation_view)

        val navController = findNavController(R.id.activity_main_navigation_host_fragment)

        /*
        //TODO: KIRIN3 ActionBar設置時に指定
        //各メニューIDを一連のIDとして渡します
        //メニューは最上位の宛先と見なされる必要があります。

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.bottom_navigation_home, R.id.bottom_navigation_dashboard, R.id.bottom_navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        */


        LoginHelper.processLogin(this, intent)

        //BottomNavigatinにNavigationを設定
        bottomNavigationView.setupWithNavController(navController)
    }

}
