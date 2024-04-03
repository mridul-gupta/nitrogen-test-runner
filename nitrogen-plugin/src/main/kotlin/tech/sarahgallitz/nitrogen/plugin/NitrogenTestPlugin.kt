package tech.sarahgallitz.nitrogen.plugin

import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.dsl.TestExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class NitrogenTestPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val config = NitrogenTestConfiguration(project)
        project.evaluationDependsOn(config.targetProjectPath)

        val envJvmSetting = System.getenv("NITROGEN_IS_RUNNING_ON_JVM")
        val shouldRunOnJvm = envJvmSetting == "true" || config.isRunningOnJVM == true

        if (shouldRunOnJvm) {
            configureJvmLibrary(config, project)
        } else {
            configureAndroidTestLibrary(config, project)
        }
    }

    private fun configureJvmLibrary(
        config: NitrogenTestConfiguration,
        project: Project
    ) {
        project.plugins.apply("com.android.library")

        project.extensions.getByType(LibraryExtension::class.java).apply {
            val androidConfig = config.androidConfiguration

            compileSdk = compileSdk ?: androidConfig.compileSdk

            defaultConfig {
                minSdk = minSdk ?: androidConfig.defaultConfig.minSdk

                testOptions {
                    unitTests {
                        isIncludeAndroidResources = true
                    }
                }
            }

            testOptions {
                unitTests {
                    isIncludeAndroidResources = true
                }
            }

            sourceSets {
                getByName("test") {
                    it.kotlin.srcDir("src/main/kotlin")
                    it.java.srcDir("src/main/kotlin")
                    it.kotlin.srcDir("src/main/java")
                    it.java.srcDir("src/main/java")
                }
            }

            sourceSets.forEach { sourceSet ->
                if (sourceSet.name != "main" && !sourceSet.name.contains("test", ignoreCase = true)) {
                    val testName = "test" + sourceSet.name.replaceFirstChar { it.uppercase() }
                    sourceSets.getByName(testName) { test ->
                        test.kotlin.srcDir("src/${sourceSet.name}/kotlin")
                        test.java.srcDir("src/${sourceSet.name}/kotlin")
                        test.kotlin.srcDir("src/${sourceSet.name}/java")
                        test.java.srcDir("src/${sourceSet.name}/java")
                    }
                }
            }

            namespace = namespace ?: androidConfig.namespace
        }

        project.dependencies.add(
            "implementation",
            "org.robolectric:robolectric:[4.10.0,5.0.0)!!4.10.3"
        )
        project.dependencies.add(
            "implementation",
            "io.github.takahirom.roborazzi:roborazzi:[1.0.0,2.0.0)!!1.10.1"
        )
        project.dependencies.add(
            "implementation",
            "io.github.takahirom.roborazzi:roborazzi-compose:[1.0.0,2.0.0)!!1.10.1"
        )
        project.dependencies.add(
            "implementation",
            "com.jraska:falcon:[2.0.0,3.0.0)!!2.2.0"
        )
    }

    private fun configureAndroidTestLibrary(
        config: NitrogenTestConfiguration,
        project: Project
    ) {
        project.plugins.apply("com.android.test")

        project.extensions.getByType(TestExtension::class.java).apply {
            project.evaluationDependsOn(config.targetProjectPath)

            targetProjectPath = config.targetProjectPath

            val androidConfig = config.androidConfiguration

            compileSdk = compileSdk ?: androidConfig.compileSdk

            defaultConfig {
                minSdk = minSdk ?: androidConfig.defaultConfig.minSdk
                targetSdk = targetSdk ?: androidConfig.defaultConfig.targetSdk

                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }

            namespace = namespace ?: androidConfig.namespace
        }

        project.dependencies.add(
            "compileOnly",
            "org.robolectric:robolectric:[4.10.0,5.0.0)!!4.10.3"
        )
        project.dependencies.add(
            "compileOnly",
            "io.github.takahirom.roborazzi:roborazzi:[1.0.0,2.0.0)!!1.10.1"
        )
        project.dependencies.add(
            "compileOnly",
            "io.github.takahirom.roborazzi:roborazzi-compose:[1.0.0,2.0.0)!!1.10.1"
        )
    }
}
