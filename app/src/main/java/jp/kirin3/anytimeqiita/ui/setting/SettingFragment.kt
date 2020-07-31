package jp.kirin3.anytimeqiita.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import jp.kirin3.anytimeqiita.R
import jp.kirin3.anytimeqiita.helper.LoginHelper
import jp.kirin3.anytimeqiita.model.AuthenticatedUserModel
import jp.kirin3.anytimeqiita.ui.reading.LoginModel

class SettingFragment : Fragment() {

    private lateinit var settingViewModel: SettingViewModel
    private lateinit var loginButton: Button
    private lateinit var logoutButton: Button
    private lateinit var notLoggedLayout: LinearLayout
    private lateinit var loggingLayout: LinearLayout
    private lateinit var userIdTextView: TextView

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
        loginButton.setOnClickListener {
            LoginHelper.accessLoginPage(context)
//            findNavController().navigate(R.id.action_setting_to_login)
        }

        logoutButton = root.findViewById(R.id.fragment_setting_logout_button)
        logoutButton.setOnClickListener {
            LoginHelper.clearLoginAllInfo(context)
            changeNotLoggedModeInterface()
//            findNavController().navigate(R.id.action_setting_to_login)
        }

        notLoggedLayout = root.findViewById(R.id.fragment_setting_not_logged_in_layout)
        loggingLayout = root.findViewById(R.id.fragment_setting_logged_in_layout)

        userIdTextView = root.findViewById(R.id.fragment_setting_user_id)

        LoginHelper.getStatusFromModel(context).also {
            if (it == LoginModel.Status.COMPLETE) {
                changeLoggingModeInterface()
                displayAuthentiatedUserId()
            }
        }

/*
        val titleTextView: TextView = root.findViewById(R.id.text_setting)
        settingViewModel.text.observe(this, Observer {
            titleTextView.text = it
        })
*/
        return root
    }


    fun changeLoggingModeInterface() {
        notLoggedLayout.visibility = View.GONE
        loggingLayout.visibility = View.VISIBLE
    }

    fun changeNotLoggedModeInterface() {
        notLoggedLayout.visibility = View.VISIBLE
        loggingLayout.visibility = View.GONE
    }

    fun displayAuthentiatedUserId() {
        val userId = AuthenticatedUserModel.getAuthenticatedUserIdFromCashe()
        userIdTextView.setText(userId)
    }
}