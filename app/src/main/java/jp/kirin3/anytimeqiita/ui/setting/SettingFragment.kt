package jp.kirin3.anytimeqiita.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import jp.kirin3.anytimeqiita.R
import jp.kirin3.anytimeqiita.helper.LoginHelper
import jp.kirin3.anytimeqiita.model.AuthenticatedUserModel
import jp.kirin3.anytimeqiita.ui.reading.LoginModel

class SettingFragment : Fragment() {

    private lateinit var loginButton: Button
    private lateinit var logoutButton: Button
    private lateinit var notLoggedLayout: LinearLayout
    private lateinit var loggingLayout: LinearLayout
    private lateinit var userIdTextView: TextView


    private lateinit var linkTextView: TextView

    /*
        private val loginButton: Button by lazy { fragment_setting_login_button }
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_setting, container, false)

        notLoggedLayout = root.findViewById(R.id.fragment_setting_not_logged_in_layout)
        loggingLayout = root.findViewById(R.id.fragment_setting_logged_in_layout)
        userIdTextView = root.findViewById(R.id.fragment_setting_user_id)

        loginButton = root.findViewById(R.id.fragment_setting_login_button)
        loginButton.setOnClickListener {
            LoginHelper.accessLoginPage(context)
        }

        logoutButton = root.findViewById(R.id.fragment_setting_logout_button)
        logoutButton.setOnClickListener {
            LoginHelper.clearLoginAllInfo(context)
            changeLoggingModeInterface(false)
        }

        LoginHelper.getStatusFromModel(context).also {
            if (it == LoginModel.Status.COMPLETE) {
                changeLoggingModeInterface(true)
                displayAuthentiatedUserId()
            }
        }

        return root
    }

    fun changeLoggingModeInterface(login: Boolean) {
        if (login) {
            notLoggedLayout.visibility = View.GONE
            loggingLayout.visibility = View.VISIBLE
        } else {
            notLoggedLayout.visibility = View.VISIBLE
            loggingLayout.visibility = View.GONE
        }
    }

    fun displayAuthentiatedUserId() {
        val userId = AuthenticatedUserModel.getAuthenticatedUserIdFromCashe()
        userIdTextView.setText(userId)
    }
}