package tech.sarahgallitz.nitrogen.environment

import org.robolectric.Robolectric

object TestEnvironment {
    val isJvm: Boolean
        get() = try {
            Robolectric.getForegroundThreadScheduler()
            true
        } catch (e: Throwable) {
            false
        }

    var configuration = NitrogenConfiguration()
}
