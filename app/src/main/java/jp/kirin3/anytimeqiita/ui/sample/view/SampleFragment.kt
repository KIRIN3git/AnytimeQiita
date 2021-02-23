package jp.kirin3.anytimeqiita.ui.sample.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import jp.kirin3.anytimeqiita.MainApplication
import jp.kirin3.anytimeqiita.R
import jp.kirin3.anytimeqiita.ui.sample.presentation.SamplePresenter
import jp.kirin3.anytimeqiita.ui.sample.presentation.SampleViewModel
import kirin3.jp.mljanken.util.LogUtils.LOGD
import kirin3.jp.mljanken.util.LogUtils.LOGI
import kotlinx.android.synthetic.main.fragment_sample.*
import javax.inject.Inject


class SampleFragment : Fragment(), SampleContract {

    companion object {
        /**
         * テスト画面のフラグメンを作成する
         */
        fun newInstance(): SampleFragment {
            return SampleFragment()
        }
    }

//    lateinit var application: Application

    @Inject
    lateinit var presenter: SamplePresenter

//    private val component = DaggerAppComponent.builder()
//        .appModule(AppModule())
//        .build()


    override fun onAttach(context: Context) {
        super.onAttach(context)
        val nonNullActivity = activity ?: return

        val viewModel = ViewModelProviders.of(nonNullActivity).get(SampleViewModel::class.java)

        MainApplication.component.inject(this)

//        this.component.inject(this)  // <-- これ

        LOGI("aaaaa ${presenter.greet()}")
//        // Inject
//        (application as? MainApplication)?.getComponent()?.inject(this)
//
//        presenter.setup(this, viewModel)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        LOGD("")

//        this.component.inject(this)  // <-- これ

//        LogUtils.LOGI("aaaaa ${this.viewModel.greet()}")

        val root = inflater.inflate(R.layout.fragment_sample, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragment_setting_logout_button.text = "aaa"

    }

    override fun showButton(id:Int) {
        TODO("Not yet implemented")
    }


}