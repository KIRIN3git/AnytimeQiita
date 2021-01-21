package jp.kirin3.anytimeqiita

import org.junit.Rule
import org.junit.Test


class MainActivityTest

@Rule
var activityTestRule: ActivityTestRule<MainActivity> =
    ActivityTestRule(MainActivity::class.java, false, false)

@Rule
public val mActivityRule: ActivityTestRule<MainActivity> = ActivityTestRule(javaClass<MainActivity>())

@Test
fun check_start_activity() {
    val activity: MainActivity = activityTestRule.launchActivity(null)
    /* ここにテストする内容を書く */
}