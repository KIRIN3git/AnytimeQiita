package jp.kirin3.anytimeqiita.ui.setting

import android.content.Context
import jp.kirin3.anytimeqiita.model.LoginModel
import kirin3.jp.mljanken.util.SettingsUtils


class SettingPresenter(
    private val settingRepository: SettingRepository,
    private val settingView: SettingContract.View
) : SettingContract.Presenter {

    // FragmentのpresenterにViewを設定
    init {
        settingView.presenter = this
    }

    /**
     * AccessTokenとAuthenticatedUser情報を連蔵して取得
     * 取得後は、viewに情報を返す
     */
    override fun loadQiitaLoginInfo(context: Context?) {
        if (context == null) return

        settingRepository.loadAccessToken(
            context,
            LoginModel.getQiitaCode(context),
            object : SettingDataSource.LoadAccessTokenCallback {
                override fun onAccessTokenLoaded() {
                    settingRepository.loadAuthenticatedUser(
                        context,
                        SettingsUtils.getAccessToken(context),
                        object : SettingDataSource.LoadAuthenticatedUserCallback {
                            override fun onAuthenticatedUserLoaded() {
                                settingView.showLoginSuccessToast()
                                settingView.setLoggingModeInterface(true)
                            }

                            override fun onAuthenticatedUserNotAvailable() {
                                settingView.showLoginailureToast()
                                settingView.setLoggingModeInterface(false)
                            }
                        })
                }

                override fun onAccessTokenNotAvailable() {
                    settingView.showLoginailureToast()
                    settingView.setLoggingModeInterface(false)
                }
            })
    }
}
