package kirin3.jp.mljanken.util

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager

object AppInfoUtils {

    /**
     * バージョンを取得します。
     */
    fun getVersionName(context: Context, packageName: String): String {
        var versionName = ""
        val pm = context.packageManager
        val info: PackageInfo
        try {
            info = pm.getPackageInfo(packageName, 0)
            versionName = info.versionName
        } catch (e: PackageManager.NameNotFoundException) {
        }

        return versionName
    }
}
