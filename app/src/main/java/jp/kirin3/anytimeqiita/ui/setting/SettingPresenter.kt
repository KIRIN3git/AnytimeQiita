package jp.kirin3.anytimeqiita.ui.setting

import android.content.Context
import kirin3.jp.mljanken.util.SettingsUtils


class SettingPresenter(
    private val settingRepository: SettingRepository,
    private val settingView: SettingContract.View
) : SettingContract.Presenter {

    // FragmentのpresenterにViewを設定
    init {
        settingView.presenter = this
    }

    override fun loadQiitaLoginInfo(context: Context?) {
        if (context == null) return

        settingRepository.loadAccessToken(
            context,
            SettingsUtils.getQiitaCode(context),
            object : SettingDataSource.LoadAccessTokenCallback {
                override fun onAccessTokenLoaded() {
                    settingRepository.loadAuthenticatedUser(
                        context,
                        SettingsUtils.getQiitaAccessToken(context),
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
