package jp.kirin3.anytimeqiita.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import jp.kirin3.anytimeqiita.BaseFragment
import jp.kirin3.anytimeqiita.R
import jp.kirin3.anytimeqiita.injection.Injection
import jp.kirin3.anytimeqiita.model.AuthenticatedUserModel
import jp.kirin3.anytimeqiita.ui.reading.LoginModel
import kirin3.jp.mljanken.util.LogUtils.LOGI
import kirin3.jp.mljanken.util.SettingsUtils

class SettingFragment : BaseFragment(), SettingContract.View {

    private lateinit var loginButton: Button
    private lateinit var sampleButton: Button
    private lateinit var logoutButton: Button
    private lateinit var notLoggedLayout: LinearLayout
    private lateinit var loggingLayout: LinearLayout
    private lateinit var userIdTextView: TextView
    private lateinit var useExternalToggleButton: ToggleButton
    private var isLoginMode: Boolean = false
    override lateinit var presenter: SettingContract.Presenter

    companion object {
        val IS_LOGIN_MODE = "IS_REFRESH"
    }

    /*
        private val loginButton: Button by lazy { fragment_setting_login_button }
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_setting, container, false)

        setTitle(getString(R.string.title_setting))

        presenter = SettingPresenter(
            Injection.provideSettingRepository(),
            this
        )

        getParam()
        LOGI(" isLoginMode3   " + isLoginMode)
        notLoggedLayout = root.findViewById(R.id.fragment_setting_not_logged_in_layout)
        loggingLayout = root.findViewById(R.id.fragment_setting_logged_in_layout)
        userIdTextView = root.findViewById(R.id.fragment_setting_user_id)

        loginButton = root.findViewById(R.id.login_button)
        loginButton.setOnClickListener {
            LoginModel.accessQiitaLoginPage(context)
        }

//        sampleButton = root.findViewById(R.id.sample_button)
//        sampleButton.setOnClickListener {
//            val intent = Intent(context, SampleActivity::class.java)
//            startActivity(intent)
//        }

        logoutButton = root.findViewById(R.id.fragment_setting_logout_button)
        logoutButton.setOnClickListener {
            LoginModel.clearAllLoginInfo(context)
            setLoggingModeInterface(false)
        }

        useExternalToggleButton = root.findViewById(R.id.use_external_browser_toggle_button)
        useExternalToggleButton.isChecked = SettingsUtils.getUseExternalBrowser(context)
        useExternalToggleButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                SettingsUtils.setUseExternalBrowser(context, true)
            } else {
                SettingsUtils.setUseExternalBrowser(context, false)
            }
        }

        if (isLoginMode) {
            presenter.loadQiitaLoginInfo(context)
        } else {
            AuthenticatedUserModel.setAuthnticatedUserFromDbToCache()
            if (LoginModel.isLoginCompleted(context)) {
                setLoggingModeInterface(true)
            }
        }

        return root
    }

    fun getParam() {
        arguments?.run {
            if (getBoolean(IS_LOGIN_MODE)) {
                isLoginMode = true
            }
        }
    }

    override fun setLoggingModeInterface(login: Boolean) {
        if (login) {
            notLoggedLayout.visibility = View.GONE
            loggingLayout.visibility = View.VISIBLE
            displayAuthentiatedUserId()
        } else {
            notLoggedLayout.visibility = View.VISIBLE
            loggingLayout.visibility = View.GONE
        }
    }

    fun displayAuthentiatedUserId() {
        val userId = AuthenticatedUserModel.getAuthenticatedUserIdFromCacheOrDb()
        userIdTextView.setText(userId)
    }

    override fun showLoginSuccessToast() {
        Toast.makeText(
            context,
            this.getString(R.string.login_success),
            Toast.LENGTH_LONG
        ).show()
    }

    override fun showLoginailureToast() {
        Toast.makeText(
            context,
            this.getString(R.string.login_fail),
            Toast.LENGTH_LONG
        ).show()
    }
}