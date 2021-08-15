package jp.kirin3.anytimeqiita.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.Gson
import jp.kirin3.anytimeqiita.BaseFragment
import jp.kirin3.anytimeqiita.MainActivity
import jp.kirin3.anytimeqiita.R
import jp.kirin3.anytimeqiita.data.SettingCheckBoxData
import jp.kirin3.anytimeqiita.injection.Injection
import jp.kirin3.anytimeqiita.model.LoginModel
import kirin3.jp.mljanken.util.SettingsUtils
import kotlinx.android.synthetic.main.fragment_setting.*

class SettingFragment : BaseFragment(), SettingContract.View {

    override lateinit var presenter: SettingContract.Presenter

    /**
     *  ログインモード判定
     *  Qiitaログイン画面からの戻ってきた場合、SettingFragmentでログイン処理を行う
     */
    private var isLoginMode: Boolean = false

    companion object {
        // Qiita画面から戻ってきたパラメータ
        const val COME_BACK_FROM_QIITA_LOGIN = "COME_BACK_FROM_QIITA_LOGIN"

        fun newInstance(): SettingFragment {
            return SettingFragment()
        }
    }


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

        val acti = (activity as MainActivity)
        acti.addFragment()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        login_button.setOnClickListener {
            LoginModel.accessQiitaLoginPage(context)
        }

        fragment_setting_logout_button.setOnClickListener {
            LoginModel.clearAllLoginInfo(context)
            LoginModel.clearAllUserSetting(context)
            setLoggingModeInterface(false)
        }

        if (isLoginMode) {
            presenter.loadQiitaLoginInfo(context)
        } else {
            if (LoginModel.isLoginCompleted(context)) {
                setLoggingModeInterface(true)
            }
        }

        settingCheckBox()
    }

    private fun settingCheckBox() {

        val beforeSettingCheckBoxData =
            if (SettingsUtils.hasSettingCheckBoxData(context)) {
                SettingsUtils.getSettingCheckBoxData(context)
            } else {
                SettingCheckBoxData()
            }

        var afterSettingCheckBoxData = beforeSettingCheckBoxData

        if (afterSettingCheckBoxData == null || beforeSettingCheckBoxData == null) return

        use_external_browser_check_box.isChecked =
            beforeSettingCheckBoxData.reading_use_external_browse
        use_external_browser_check_box.setOnCheckedChangeListener { _, isChecked ->
            afterSettingCheckBoxData.reading_use_external_browse = isChecked
            setCheckBoxSp(afterSettingCheckBoxData)
        }

        show_icon_check_box.isChecked =
            beforeSettingCheckBoxData.setting_show_icon
        show_icon_check_box.setOnCheckedChangeListener { _, isChecked ->
            afterSettingCheckBoxData.setting_show_icon = isChecked
            setCheckBoxSp(afterSettingCheckBoxData)
        }

        show_lgtm_check_box.isChecked =
            beforeSettingCheckBoxData.setting_show_lgtm
        show_lgtm_check_box.setOnCheckedChangeListener { _, isChecked ->
            afterSettingCheckBoxData.setting_show_lgtm = isChecked
            setCheckBoxSp(afterSettingCheckBoxData)
        }

        show_update_time_check_box.isChecked =
            beforeSettingCheckBoxData.setting_update_time
        show_update_time_check_box.setOnCheckedChangeListener { _, isChecked ->
            afterSettingCheckBoxData.setting_update_time = isChecked
            setCheckBoxSp(afterSettingCheckBoxData)
        }
    }

    private fun setCheckBoxSp(afterSettingCheckBoxData: SettingCheckBoxData) {
        val json = Gson().toJson(afterSettingCheckBoxData)
        SettingsUtils.setSettingCheckBoxData(context, json)
    }


    private fun getParam() {
        arguments?.run {
            if (getBoolean(COME_BACK_FROM_QIITA_LOGIN)) {
                isLoginMode = true
            }
        }
    }

    override fun setLoggingModeInterface(login: Boolean) {
        if (login) {
            fragment_setting_not_logged_in_layout.visibility = View.GONE
            fragment_setting_logged_in_layout.visibility = View.VISIBLE
            displayAuthenticatedUserId()
        } else {
            fragment_setting_not_logged_in_layout.visibility = View.VISIBLE
            fragment_setting_logged_in_layout.visibility = View.GONE
        }
    }

    private fun displayAuthenticatedUserId() {
        val userId = LoginModel.getAuthenticatedUserId()
        fragment_setting_user_id.setText(userId)
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