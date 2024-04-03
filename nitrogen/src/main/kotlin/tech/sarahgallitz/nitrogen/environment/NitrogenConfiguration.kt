package tech.sarahgallitz.nitrogen.environment

import androidx.test.platform.app.InstrumentationRegistry

open class NitrogenConfiguration {
    open fun getOnDeviceScreenshotsDir(): String {
        return InstrumentationRegistry.getInstrumentation().targetContext.filesDir.absolutePath + "/test-screenshots/"
    }

    open fun getJvmScreenshotsDir(): String {
        return "build/outputs/test-screenshots/"
    }
}
