package kirin3.jp.mljanken.util

import android.content.Context
import android.view.View
//import com.google.android.gms.ads.*
//import kirin3.jp.mljanken.R
import kirin3.jp.mljanken.util.LogUtils.LOGD


object AdmobHelper {

    /*
    private val TAG = LogUtils.makeLogTag(AdmobHelper::class.java)


    var interstitialAdNextGame: InterstitialAd? = null
    var fragment_folders_dialog: String? = null

    // 初回アクティビティのみ
    // バナーもインターステシャルも必要
    fun initializeAdmob(context: Context) {
        LOGD(TAG, "initializeAdmob")
        MobileAds.initialize(
            context.applicationContext,
            context.applicationContext!!.resources.getString(R.string.admob_app_id)
        )
    }

    /*
     * バナー呼び出し
     * バナーを使うアクティビティのonCreateに書く必要あり
     * AdmobHelper.setAdmob((AdView)findViewById(R.id.adView));
     */
    fun loadBanner(view: AdView) {
        LOGD(TAG, "loadBanner")
        val adRequest = AdRequest.Builder().build()
        view.loadAd(adRequest)
    }

    /*
     * 作成中
     */
    fun setAdsInXml(context: Context, view: View) {

        LOGD(TAG, "setAdsInXml")

        val mAdView = AdView(context)
        mAdView.adSize = AdSize.BANNER
        mAdView.adUnitId = "ca-app-pub-3940256099942544/6300978111"
        (view as AdView).addView(mAdView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }

    /*
     * インターステシャル設定
     * onCreateで呼ぶと一度しかロードできないので注意
     */
    fun setInterstitialNextGame() {
        LOGD(TAG, "setInterstitialNextGame")
        // イニシャライズ
//        interstitialAdNextGame = InterstitialAd(sAppContext!!)
//☆        interstitialAdNextGame!!.adUnitId = sAppContext!!.resources.getString(R.string.interstitial_ad_unit_id_test)
        // ロード
        interstitialAdNextGame!!.loadAd(AdRequest.Builder().build())
    }

    /*
     * インターステシャル呼び出し
     * 成功時:true,失敗時:false
     */
    fun loadInterstitialNextGame(): Boolean {
        LOGD(TAG, "loadInterstitialNextGame")
        if (interstitialAdNextGame!!.isLoaded) {
            interstitialAdNextGame!!.show()
            return true
        } else
            return false// まだロードできていない or ロードしていない
    }
    */
    /*
     * インターステシャルの閉じた判定サンプル
     */
    /*
        AdmobHelper.interstitialAdNextGame.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                Intent intent = new Intent(appContext, TopActivity.class);
                startActivity(intent);
            }
        });
 */
}
