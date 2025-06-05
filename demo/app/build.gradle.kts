@file:Suppress("UnstableApiUsage")

import java.util.Locale

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

enum class Brand(
    val brandName: String,
    val applicationId: String
) {
    SEEK("SEEK", "au.com.seek"),
    JOBSDB("JobsDB", "com.jobsdb"),
    JOBSTREET("JobStreet", "com.jobstreet.jobstreet");

    /**
     * E.g. Seek, Jobsdb, Jobstreet
     */
    val taskName: String
        get() = slug.replaceFirstChar { it.uppercase() }

    /**
     * E.g. seek, jobsdb, jobstreet
     */
    val slug: String
        get() = brandName.lowercase(Locale.ROOT)
}

//sourceSets {
//    project.tasks.withType<Test> {
//        testClassesDirs = testClassesDirs.plus(sourceSets.getByName("main").output.classesDirs)
//    }
//}

android {
    namespace = "demo.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "demo.app"

        minSdk = 21
        targetSdk = 34

        versionCode = 1
        versionName = "1.0"

        val brand = "brand"
        val mode = "mode"
        val production = "production"
        val dev = "dev"

        flavorDimensions(brand, mode)

        productFlavors {
            create(Brand.SEEK.slug) {
                dimension = brand
            }
            create(Brand.JOBSDB.slug) {
                dimension = brand
            }
            create(Brand.JOBSTREET.slug) {
                dimension = brand
            }
            create(production) {
                dimension = mode
            }
            create(dev) {
                dimension = mode
            }
        }
        variantFilter {
            flavors.forEach { it ->
                ignore = name.lowercase(Locale.ROOT)
                    .contains(dev + "release") && buildType.name == "release"
            }
        }

        testOptions {
            unitTests {
                isIncludeAndroidResources = true
            }
        }
    }

//    tasks.withType<Test> {
//        testClassesDirs += project.sourceSets.getByName("test").output.classesDirs
//    }

//    sourceSets.getByName("main").java.srcDirs.forEach { dir ->
//        tasks.withType<Test> {
//            testClassesDirs = testClassesDirs.plus(project.fileTree(dir))
//        }
//    }

//    tasks.withType<Test> {
//        val androidExt =
//            project.extensions.getByType(com.android.build.gradle.internal.dsl.BaseAppModuleExtension::class.java)
//        val mainOutput = androidExt.sourceSets.getByName("main").kotlin.srcDirs(
//            "src/main/kotlin",
//            "src/main/java"
//        )
//
//        testClassesDirs = files(testClassesDirs, mainOutput)
//        classpath = files(classpath, mainOutput)
//    }

//    tasks.withType<Test> {
//        // First make sure testClassesDirs is initialized if null
//        val currentTestClassesDirs = testClassesDirs ?: files()
//
//        // Get main source set's output class dirs
//        val mainSourceSet = project.extensions.getByType(SourceSetContainer::class.java).getByName("main")
//
//        // Combine using files() which handles nulls safely
//        testClassesDirs = files(testClassesDirs, mainSourceSet.output.classesDirs)
//        classpath = files(classpath, mainSourceSet.output.classesDirs)
//    }

//    project.tasks.withType<Test> {
//        val mainSourceSet = sourceSets.getByName("main")
//        testClassesDirs += files(mainSourceSet.kotlin.srcDir("src/main/kotlin"))
//        testClassesDirs = testClassesDirs.plus(sourceSets.main.get().output.classesDirs)
//
//    }

    buildFeatures { compose = true }
    composeOptions { kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get() }
}

dependencies {
    implementation(platform(libs.compose.bom))

    implementation(libs.androidx.compose.activity)
    implementation(libs.compose.material3)

    implementation(libs.compose.uitooling.preview)
    testImplementation(libs.junit)

    debugImplementation(libs.compose.uitooling)
    debugImplementation(libs.compose.uitest.manifest)
}
