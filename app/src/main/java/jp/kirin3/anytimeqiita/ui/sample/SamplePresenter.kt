package jp.kirin3.anytimeqiita.ui.sample

/**
 * サンプル画面のプレゼンター実装規約
 */
interface SamplePresenter {

    /**
     * プレゼンターをセットアップする
     *
     * @param view サンプル画面の ViewContract
     * @param viewModel サンプル画面の ViewModel
     */
    fun setup(
        view: SampleContract,
        viewModel: SampleViewModel
    )

    /**
     * 入力用 TextEdit を初期表示する
     */
    fun initTextEdit()

    fun greet(): String

    fun loadData()
}