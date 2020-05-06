package jp.kirin3.anytimeqiita.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import jp.kirin3.anytimeqiita.R
import jp.kirin3.anytimeqiita.helper.LoginHelper

class SettingFragment : Fragment() {

    private lateinit var settingViewModel: SettingViewModel
    private lateinit var loginButton:Button

    /*
        private val loginButton: Button by lazy { fragment_setting_login_button }
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settingViewModel =
            ViewModelProviders.of(this).get(SettingViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_setting, container, false)

        loginButton = root.findViewById(R.id.fragment_setting_login_button)
        loginButton.setOnClickListener{
            LoginHelper.accessLoginPage(context)
//            findNavController().navigate(R.id.action_setting_to_login)
        }
/*
        val textView: TextView = root.findViewById(R.id.text_setting)
        settingViewModel.text.observe(this, Observer {
            textView.text = it
        })
*/
        return root
    }
}