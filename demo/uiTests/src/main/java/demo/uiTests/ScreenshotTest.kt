package demo.uiTests

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import demo.app.MainActivity
import kotlin.test.Test
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import tech.sarahgallitz.nitrogen.environment.DeviceQualifiers
import tech.sarahgallitz.nitrogen.runner.NitrogenTestRunner
import tech.sarahgallitz.nitrogen.screenshots.takeScreenshot

@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [33], qualifiers = DeviceQualifiers.Pixel5)
@RunWith(NitrogenTestRunner::class)
class ScreenshotTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun screenshot_MainActivity() {
        composeTestRule.activity.takeScreenshot("MainActivity")
    }

    @Test
    fun screenshot_HomeScreen() {
        composeTestRule.onRoot().takeScreenshot("HomeScreen")
    }
}
