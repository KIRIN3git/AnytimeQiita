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
import jp.kirin3.anytimeqiita.R
import jp.kirin3.anytimeqiita.ui.graph.GraphFragment
import kirin3.jp.mljanken.util.LogUtils.LOGD

private const val NUM_PAGES = 5

class RecordFragment : Fragment() {

    private lateinit var recordViewModel: RecordViewModel

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        LOGD("")
        recordViewModel =
            ViewModelProviders.of(this).get(RecordViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_record, container, false)

        viewPager = root.findViewById(R.id.pager)
        tabLayout = root.findViewById(R.id.tab_layout)
        childFragmentManager
        val pagerAdapter = ScreenSlidePagerAdapter(this)
        viewPager.adapter = pagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = "OBJECT ${(position + 1)}"
        }.attach()

        return root
    }

    private class ScreenSlidePagerAdapter(fa: Fragment) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int {
            return NUM_PAGES
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> GraphFragment()
                1 -> GraphFragment()
                else -> GraphFragment()
            }
        }
    }
}