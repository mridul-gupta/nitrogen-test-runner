import java.util.Locale
import org.gradle.kotlin.dsl.test
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree.Companion.test

plugins {
    alias(libs.plugins.nitrogen.test)
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

sourceSets {
    // Add main sources to test sourceset
    project.tasks.withType<Test> {
        println("MGMGMG sourceSets: [${sourceSets.names}] ${this.name} $testClassesDirs")

        if (this.name == "jobsdbProductionDebug") {
            println("MGMGMG adding jobsdbProductionDebug to testClassesDirs")
            testClassesDirs =
                files(
                    testClassesDirs,
                    sourceSets.getByName("jobsdbProductionDebug").output.classesDirs
                )
        }
        if (this.name == "testJobsdbProductionDebug") {
            println("MGMGMG adding testJobsdbProductionDebug to testClassesDirs")
            testClassesDirs +=
                files(
                    testClassesDirs,
                    sourceSets.getByName("testJobsdbProductionDebug").output.classesDirs
                )
        }
    }
}
//
//    // Configure custom source sets
//    Brand.values().forEach { brand ->
//        val brandSourceSet = brand.slug
////        getByName(brandSourceSet) {
////            java.srcDir("src/$brandSourceSet/java")
////            kotlin("src/$brandSourceSet/kotlin")
////        }
//
//        // Make test tasks aware of these source sets
////        project.tasks.withType<Test> {
////            if (name.contains(brand.taskName, ignoreCase = true)) {
////                testClassesDirs = testClassesDirs.plus(sourceSets.getByName(brandSourceSet).output.classesDirs)
////            }
////        }
//    }
//}
sourceSets {
    // Create a list of source set names we expect to be available


    // Make sure source sets exist before trying to configure them
//    Brand.values().forEach { brand ->
//        val brandSlug = brand.slug
//        if (!sourceSets.names.contains(brandSlug)) {
//            sourceSets.create(brandSlug)
//            println("MGMGMG Created source set: $brandSlug")
//        }
//
//        val testBrandSlug = "test$brandSlug"
//        if (!sourceSets.names.contains(testBrandSlug)) {
//            sourceSets.create(testBrandSlug)
//            println("MGMGMG Created source set: $testBrandSlug")
//        }
//    }

    println("MGMGMG Expected source sets: ${sourceSets.names}")

//    sourceSets.create("jobsdbProductionDebug")

    println("MGMGMG Available source sets after creation: ${sourceSets.names}")

    project.tasks.withType<Test> {
        if (testTask.name == "testJobsdbProductionDebugUnitTest") {
            testTask.testClassesDirs = files(testTask.testClassesDirs, sourceSets.getByName("main").output.classesDirs)
        }
//            println("MGMGMG testClassesDirs before for task ${testTask.name}: ${testTask.testClassesDirs}")
//            testTask.testClassesDirs = files(
//                testTask.testClassesDirs,
//                sourceSets.findByName("jobsdbProductionDebug")?.output?.classesDirs
//            )
//            testTask.testClassesDirs = files(
//                testTask.testClassesDirs,
//                sourceSets.findByName("testJobsdbProductionDebug")?.output?.classesDirs
//            )
//            println("MGMGMG testClassesDirs after for task ${testTask.name}: ${testTask.testClassesDirs}")
//        }
    }


    // Configure test tasks to include source sets by matching task name patterns
//    gradle.taskGraph.whenReady {
//        println("MGMGMG All tasks: ${allTasks.map { task -> task.name }}")
//
//        project.tasks.withType<Test>().forEach { testTask ->
//
//            val taskName = testTask.name
//            println("MGMGMG Configuring test task: $taskName")
//
//            // Find matching source set based on task name pattern
//            Brand.values().forEach { brand ->
//                val brandSlug = brand.slug
//                val brandTaskName = brand.taskName
//
//                when {
//                    // Regular variant test task (e.g. seekProductionDebugUnitTest)
//                    taskName.startsWith(brandSlug) && taskName.contains("UnitTest") -> {
//                        val sourceSet = sourceSets.findByName(brandSlug)
//                        if (sourceSet != null) {
//                            println("MGMGMG Adding source set $brandSlug to $taskName")
//                            testTask.testClassesDirs = files(testTask.testClassesDirs, sourceSet.output.classesDirs)
//                        }
//                    }
//                    // Test variant test task (e.g. testSeekProductionDebugUnitTest)
//                    taskName.startsWith("test$brandTaskName") && taskName.contains("UnitTest") -> {
//                        val sourceSet = sourceSets.findByName("test$brandSlug")
//                        if (sourceSet != null) {
//                            println("MGMGMG Adding source set test$brandSlug to $taskName")
//                            testTask.testClassesDirs = files(testTask.testClassesDirs, sourceSet.output.classesDirs)
//                        }
//                    }
//                }
//            }
//        }
//    }
}
//sourceSets {
//    // Create map of task names to their corresponding source sets
//    val variantToSourceSet = mutableMapOf<String, String>()
//
//    Brand.values().forEach { brand ->
//        val brandSlug = brand.taskName
//        // Map each brand's production/debug variant to its source set
//        variantToSourceSet["${brandSlug}ProductionDebugUnitTest"] = "${brandSlug}ProductionDebug"
//        variantToSourceSet["test${brand.taskName}ProductionDebugUnitTest"] = "test${brand.taskName}ProductionDebug"
//    }
//
//    println("MGMGMG Available source sets: ${sourceSets.names}")
//
//    // Configure test tasks to include the appropriate source sets
//    project.tasks.withType<Test> {
//        val taskName = this.name
//        println("MGMGMG Configuring test task: $taskName {$variantToSourceSet}")
//
//        // For standard test tasks (not starting with "test")
//        variantToSourceSet[taskName]?.let { sourceSetName ->
//            try {
//                val sourceSet = sourceSets.findByName(sourceSetName)
//                if (sourceSet != null) {
//                    println("MGMGMG Adding source set $sourceSetName to $taskName")
//                    testClassesDirs = files(testClassesDirs, sourceSet.output.classesDirs)
//                } else {
//                    println("MGMGMG Source set $sourceSetName not found")
//                }
//            } catch (e: Exception) {
//                println("MGMGMG Error configuring source set for $taskName: ${e.message}")
//            }
//        }
//    }
//}

android {
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
            ignore =
                name.lowercase(Locale.ROOT).contains(dev + "release") && buildType.name == "release"
        }
    }

    defaultConfig {
        testApplicationId = "demo.uiTests"
    }

    buildFeatures { compose = true }
    composeOptions { kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get() }

    tasks.withType<Test>().configureEach {
        doFirst {
            if (name.contains("jobsdb", ignoreCase = true)) {
                println("========== TEST CLASSPATH for $name ==========")
                println("Test classes dirs: $testClassesDirs")
                println("Classpath entries:")
                classpath.files.forEach { file ->
                    println("  $file")
                }
            }
        }
    }
}

dependencies {
    implementation(project(":demo:app"))

    implementation(embeddedKotlin("test-junit"))

    implementation(libs.androidx.test.runner)
    implementation(libs.androidx.test.rules)
    implementation(libs.androidx.test.espresso)

    implementation(platform(libs.compose.bom))
    implementation(libs.compose.material3)
    implementation(libs.compose.uitest)

    implementation(project(":nitrogen"))
}
