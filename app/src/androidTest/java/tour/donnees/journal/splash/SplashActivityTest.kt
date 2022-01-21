package tour.donnees.journal.splash

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class SplashActivityTest {

    @get:Rule(order = 0) //need to be the first rule
    var hiltRule = HiltAndroidRule(this)

    @Before fun init() {
        hiltRule.inject()
    }

    @Test
    fun launchMainPageActivityTest() {
        //val scenario = ActivityScenario.launch(SplashActivity::class.java)
    }

}