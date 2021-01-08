package tech.central.showcase

import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.util.HumanReadables
import androidx.test.espresso.util.TreeIterables
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeoutException


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class ExampleInstrumentedTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule<MainActivity>(MainActivity::class.java)


    @Test
    fun testShowSnackBar() {
        onView(withText("POST")).perform(click())   //Go to PostFragment first time
        //Wait for snackBar displayed
        onView(isRoot()).perform(waitId(com.google.android.material.R.id.snackbar_text, 5000))
        //Assert snackBar displayed correctly
        onView(withId(com.google.android.material.R.id.snackbar_text)).check(matches(withText(R.string.post_loaded)))

        //Wait 5 seconds for snackBar was dismissed then press back
        onView(isRoot()).perform(waitFor(5000))
        Espresso.pressBack()

        onView(withText("POST")).perform(click())   //Go to PostFragment second time
        //SnackBar should not be displayed on second time
        onView(withId(com.google.android.material.R.id.snackbar_text)).check(doesNotExist())

        onView(withText("qui est esse")).perform(click())   //Go to DetailFragment
        Espresso.pressBack()

        //SnackBar should not be displayed when back from detail screen
        onView(withId(com.google.android.material.R.id.snackbar_text)).check(doesNotExist())
    }
}


/**
 * Waiting for a view to be displayed for a specific time.
 */
fun waitId(viewId: Int, millis: Long): ViewAction? {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> {
            return isRoot()
        }

        override fun getDescription(): String {
            return "wait for a specific view with id <$viewId> during $millis millis."
        }

        override fun perform(uiController: UiController, view: View?) {
            uiController.loopMainThreadUntilIdle()
            val startTime = System.currentTimeMillis()
            val endTime = startTime + millis
            val viewMatcher: Matcher<View> = withId(viewId)
            do {
                for (child in TreeIterables.breadthFirstViewTraversal(view)) {
                    // found view with required ID
                    if (viewMatcher.matches(child)) {
                        return
                    }
                }
                uiController.loopMainThreadForAtLeast(50)
            } while (System.currentTimeMillis() < endTime)
            throw PerformException.Builder()
                    .withActionDescription(this.description)
                    .withViewDescription(HumanReadables.describe(view))
                    .withCause(TimeoutException())
                    .build()
        }
    }
}

/**
 * Perform action of waiting for a specific time.
 */
fun waitFor(millis: Long): ViewAction? {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> {
            return isRoot()
        }

        override fun getDescription(): String {
            return "Wait for $millis milliseconds."
        }

        override fun perform(uiController: UiController, view: View) {
            uiController.loopMainThreadForAtLeast(millis)
        }
    }
}