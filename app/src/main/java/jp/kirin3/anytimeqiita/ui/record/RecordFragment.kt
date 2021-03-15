package jp.kirin3.anytimeqiita.ui.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import jp.kirin3.anytimeqiita.BaseFragment
import jp.kirin3.anytimeqiita.R
import jp.kirin3.anytimeqiita.ui.graph.GraphFragment
import kirin3.jp.mljanken.util.LogUtils.LOGD


enum class ViewPagerMember(val position: Int) {
    DAILY(0),
    WEEKLY(1),
    MONTHLY(2)
}

class RecordFragment : BaseFragment() {

    companion object {
        public const val VIEW_PAGER_MEMBER_POSITION = "view_pager_member_position"
    }

    private lateinit var recordViewModel: RecordViewModel

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        LOGD("")

        setTitle(getString(R.string.title_record))

        recordViewModel =
            ViewModelProviders.of(this).get(RecordViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_record, container, false)

        viewPager = root.findViewById(R.id.pager)
        tabLayout = root.findViewById(R.id.tab_layout)
        childFragmentManager
        val pagerAdapter = ScreenSlidePagerAdapter(this)
        viewPager.adapter = pagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                ViewPagerMember.DAILY.position -> tab.text = "日別"
                ViewPagerMember.WEEKLY.position -> tab.text = "週別"
                ViewPagerMember.MONTHLY.position -> tab.text = "月別"
            }
        }.attach()

        return root
    }

    private class ScreenSlidePagerAdapter(fa: Fragment) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int {
            return ViewPagerMember.values().size
        }

        override fun createFragment(position: Int): Fragment {
            return GraphFragment().apply {
                arguments = Bundle().apply {
                    putInt(VIEW_PAGER_MEMBER_POSITION, position)
                }
            }
        }
    }
}