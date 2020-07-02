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
import jp.kirin3.anytimeqiita.helper.AuthenticatedUserHelper
import jp.kirin3.anytimeqiita.helper.LoginHelper
import jp.kirin3.anytimeqiita.ui.reading.StatusModel

class SettingFragment : Fragment() {

    private lateinit var settingViewModel: SettingViewModel
    private lateinit var loginButton: Button
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

        notLoggedLayout = root.findViewById(R.id.fragment_setting_not_logged_layout)
        loggingLayout = root.findViewById(R.id.fragment_setting_logging_layout)

        userIdTextView = root.findViewById(R.id.fragment_setting_user_id)

        StatusModel.getStatus(context).also {
            if (it == StatusModel.Status.COMPLETE) {
                changeLoginModeInterface()
                displayAuthentiatedUserId()
            }
        }

/*
        val textView: TextView = root.findViewById(R.id.text_setting)
        settingViewModel.text.observe(this, Observer {
            textView.text = it
        })
*/
        return root
    }


    fun changeLoginModeInterface() {
        notLoggedLayout.visibility = View.GONE
        loggingLayout.visibility = View.VISIBLE
    }

    fun displayAuthentiatedUserId(){
        val userId = AuthenticatedUserHelper.getAuthenticatedUserIdFromCashe()
        userIdTextView.setText(userId)
    }
}