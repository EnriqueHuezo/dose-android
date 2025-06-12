plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.ksp)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.kotlin.compose)
    id("jacoco")
    kotlin("plugin.serialization") version "2.1.20"
}

android {
    compileSdk = libs.versions.compile.sdk.version.get().toInt()

    defaultConfig {
        applicationId = "com.waseefakhtar.doseapp.dev"
        minSdk = libs.versions.min.sdk.version.get().toInt()
        targetSdk = libs.versions.target.sdk.version.get().toInt()
        versionCode = libs.versions.version.code.get().toInt()
        versionName = libs.versions.version.name.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    androidResources {
        generateLocaleConfig = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        getByName("debug") {
            enableUnitTestCoverage = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
    packaging {
        resources.excludes.apply {
            add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
    ksp {
        arg("room.schemaLocation", "$projectDir/schemas")
    }

    namespace = "com.waseefakhtar.doseapp"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.compose.ui)
    implementation(libs.compose.material3)
    implementation(libs.compose.navigation)
    implementation(libs.compose.fundation)

    implementation(libs.compose.preview)

    implementation(libs.compose.activity)

    // Lifecycle
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.viewmodel.compose)

    // Hilt // Koin
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    ksp(libs.hilt.androidx.compiler)
    implementation(libs.hilt.navigation.compose)

    // Gson
    implementation(libs.gson) // Serializacion GSON
    implementation(libs.kotlinx.serialization.json) // Serializacion Kotlinx

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // OkHttp
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)

    // Accompanist
    implementation(libs.accompanist.permission)

    // Datastore
    implementation(libs.androidx.datastore.preferences)

    implementation(libs.androidx.appcompat)

    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    androidTestImplementation(libs.junit.ext)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.compose.junit.ui)
    debugImplementation(libs.compose.ui.tooling.debug)
    debugImplementation(libs.compose.ui.test.manifest)
}

// Jacoco configuration
tasks.withType<Test> {
    configure<JacocoTaskExtension> {
        isIncludeNoLocationClasses = true
        excludes = listOf("jdk.internal.*")
    }

    finalizedBy("jacocoTestReport")
}

tasks.register<JacocoReport>("jacocoTestReport") {
    dependsOn("testDebugUnitTest")

    group = "Reporting"
    description = "Generate Jacoco coverage reports"

    reports {
        xml.required.set(true)
        html.required.set(true)
    }

    val fileFilter = listOf(
        // Android auto-generated
        "**/R.class",
        "**/R$*.class",
        "**/BuildConfig.*",
        "**/Manifest*.*",
        "android/**/*.*",

        // Unit tests & test utilities
        "**/*Test*.*",
        "**/*TestUtils*.*",

        // Kotlin synthetic / default / anonymous classes
        "**/*\$DefaultImpls*.*",
        "**/*\$default*.*",
        "**/*\$*.*", // anonymous inner classes
        "**/*_*.*", // e.g., MyClass_123abc.class
        "**/*Companion*.*",

        // Jetpack Compose specific
        "**/*Composable*.*",
        "**/*$*composable.class",
        "**/*_compose*.class",
        "**/*ComposableSingletons*.*",
        "**/*_Impl*.*",
        "**/*Preview*.*",
        "**/*_preview_*.*",
        "**/preview/**",
        "**/ui/preview/**",
        "**/*Kt.class", // top-level Kotlin classes
        "**/*\$inlined$*.*",
        "**/*\$Lambda$*.*",
        "**/*Composer*.*",
        "**/*remember*.*",

        // Dagger/Hilt generated
        "**/di/**", // your own DI classes
        "**/*_Factory*.*",
        "**/*_HiltModules*.*",
        "**/*_HiltComponents*.*",
        "**/dagger/hilt/**",
        "**/hilt_aggregated_deps/**",
        "**/*_MembersInjector.class",
        "**/*_GeneratedInjector.class",
        "**/*_ViewModel_HiltModules*.*",
        "**/*Hilt*.*", // catch-all

        // Room (DAOs, entities, impls)
        "**/*Dao_Impl*.*",
        "**/*Database_Impl*.*",

        // Navigation generated classes
        "**/*Directions*.*",
        "**/*NavGraphDirections*.*",
        "**/*FragmentArgs*.*",
        "**/*FragmentArgsImpl*.*",

        // Compose runtime internals
        "**/androidx/compose/runtime/**",
        "**/androidx/compose/ui/tooling/**",
        "**/androidx/compose/material/**",

        "**/MedicationNotificationReceiver.class",
        "**/App.class",
        "**/MainActivity.class",
        "**/MedicationNotificationService.class",
        "**/util/MedicationType.class",
        "**/analytics/AnalyticsHelper.class",
        "**/data/repository/**",
        "**/navigation/**"
    )

    val javaClasses = fileTree("$buildDir/intermediates/javac/debug/classes") {
        exclude(fileFilter)
    }
    val kotlinClasses = fileTree("$buildDir/tmp/kotlin-classes/debug") {
        exclude(fileFilter)
    }

    classDirectories.setFrom(files(javaClasses, kotlinClasses))
    sourceDirectories.setFrom(files("${project.projectDir}/src/main/java"))
    executionData.setFrom(
        fileTree(buildDir) {
            include(
                "jacoco/testDebugUnitTest.exec",
                "outputs/unit_test_code_coverage/debugUnitTest/testDebugUnitTest.exec" // New path for AGP 7.0+
            )
        }
    )

    doLast {
        println("Jacoco report generated at: ${reports.html.outputLocation.get()}/index.html")
    }
}

tasks.register<JacocoCoverageVerification>("jacocoTestCoverageVerification") {
    outputs.upToDateWhen { false }

    dependsOn("jacocoTestReport")

    // Same configuration as jacocoTestReport
    val fileFilter = listOf(
        // Android auto-generated
        "**/R.class",
        "**/R$*.class",
        "**/BuildConfig.*",
        "**/Manifest*.*",
        "android/**/*.*",

        // Unit tests & test utilities
        "**/*Test*.*",
        "**/*TestUtils*.*",

        // Kotlin synthetic / default / anonymous classes
        "**/*\$DefaultImpls*.*",
        "**/*\$default*.*",
        "**/*\$*.*", // anonymous inner classes
        "**/*_*.*", // e.g., MyClass_123abc.class
        "**/*Companion*.*",

        // Jetpack Compose specific
        "**/*Composable*.*",
        "**/*$*composable.class",
        "**/*_compose*.class",
        "**/*ComposableSingletons*.*",
        "**/*_Impl*.*",
        "**/*Preview*.*",
        "**/*_preview_*.*",
        "**/preview/**",
        "**/ui/preview/**",
        "**/*Kt.class", // top-level Kotlin classes
        "**/*\$inlined$*.*",
        "**/*\$Lambda$*.*",
        "**/*Composer*.*",
        "**/*remember*.*",

        // Dagger/Hilt generated
        "**/di/**", // your own DI classes
        "**/*_Factory*.*",
        "**/*_HiltModules*.*",
        "**/*_HiltComponents*.*",
        "**/dagger/hilt/**",
        "**/hilt_aggregated_deps/**",
        "**/*_MembersInjector.class",
        "**/*_GeneratedInjector.class",
        "**/*_ViewModel_HiltModules*.*",
        "**/*Hilt*.*", // catch-all

        // Room (DAOs, entities, impls)
        "**/*Dao_Impl*.*",
        "**/*Database_Impl*.*",

        // Navigation generated classes
        "**/*Directions*.*",
        "**/*NavGraphDirections*.*",
        "**/*FragmentArgs*.*",
        "**/*FragmentArgsImpl*.*",

        // Compose runtime internals
        "**/androidx/compose/runtime/**",
        "**/androidx/compose/ui/tooling/**",
        "**/androidx/compose/material/**",

        "**/MedicationNotificationReceiver.class",
        "**/App.class",
        "**/MainActivity.class",
        "**/MedicationNotificationService.class",
        "**/util/MedicationType.class",
        "**/analytics/AnalyticsHelper.class",
        "**/data/repository/**",
        "**/navigation/**"
    )

    val javaClasses = fileTree("$buildDir/intermediates/javac/debug/classes") {
        exclude(fileFilter)
    }
    val kotlinClasses = fileTree("$buildDir/tmp/kotlin-classes/debug") {
        exclude(fileFilter)
    }
    val mainSrc = "${project.projectDir}/src/main/java"

    classDirectories.setFrom(files(javaClasses, kotlinClasses))
    sourceDirectories.setFrom(files(mainSrc))
    executionData.setFrom(
        fileTree(buildDir) {
            include(
                "jacoco/testDebugUnitTest.exec",
                "outputs/unit_test_code_coverage/debugUnitTest/testDebugUnitTest.exec"
            )
        }
    )

    doLast {
        val reportFile = file("$buildDir/reports/jacoco/jacocoTestReport/jacocoTestReport.xml")
        if (!reportFile.exists()) {
            println("\u001B[31m[ERROR] Jacoco XML report not found: $reportFile\u001B[0m")
            throw GradleException("Coverage report not found")
        }
        // ANSI color codes
        val RED = "\u001B[31m"
        val YELLOW = "\u001B[33m"
        val GREEN = "\u001B[32m"
        val BLUE = "\u001B[34m"
        val RESET = "\u001B[0m"
        val BOLD = "\u001B[1m"

        try {
            val factory = javax.xml.parsers.DocumentBuilderFactory.newInstance().apply {
                isValidating = false
                setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false)
            }
            val xml = factory.newDocumentBuilder().parse(reportFile)

            // Collect coverage metrics
            val metrics = mutableMapOf<String, Triple<Int, Int, Double>>()
            val counters = xml.getElementsByTagName("counter")
            var totalMissed = 0
            var totalCovered = 0

            for (i in 0 until counters.length) {
                val node = counters.item(i)
                val type = node.attributes.getNamedItem("type").nodeValue
                val missed = node.attributes.getNamedItem("missed").nodeValue.toInt()
                val covered = node.attributes.getNamedItem("covered").nodeValue.toInt()
                totalMissed += missed
                totalCovered += covered
                val percentage = if (missed + covered > 0) covered * 100.0 / (covered + missed) else 0.0
                metrics[type] = Triple(missed, covered, percentage)
            }

            val totalPercentage = if (totalCovered + totalMissed > 0) {
                totalCovered * 100.0 / (totalCovered + totalMissed)
            } else 0.0

            // Print colored output
            println("\n$BLUE$BOLD=== CODE COVERAGE REPORT ===$RESET")
            metrics.forEach { (type, data) ->
                val (missed, covered, percentage) = data
                val color = when {
                    percentage < 60.0 -> RED
                    percentage < 80.0 -> YELLOW
                    else -> GREEN
                }
                println("${type.padEnd(12)}: $color${"%.1f".format(percentage)}%$RESET ($covered covered, $missed missed)")
            }

            println("$BLUE----------------------------$RESET")
            val totalColor = when {
                totalPercentage < 60.0 -> RED
                totalPercentage < 80.0 -> YELLOW
                else -> GREEN
            }
            println("${BOLD}TOTAL COVERAGE:$RESET    $totalColor${"%.1f".format(totalPercentage)}%$RESET")
            println("$BLUE============================$RESET")

            // Add colored warning messages
            when {
                totalPercentage < 60.0 -> {
                    println("\n$RED$BOLD[WARNING] Overall coverage is below 60% - consider adding more tests$RESET")
                }
                totalPercentage < 80.0 -> {
                    println("\n$YELLOW[NOTE] Coverage could be improved (currently below 80%)$RESET")
                }
                else -> {
                    println("\n$GREEN[OK] Coverage meets recommended standards$RESET")
                }
            }
        } catch (e: Exception) {
            throw GradleException("${RED}Failed to parse coverage report: ${e.message}$RESET")
        }
    }
}

// Add this to ensure verification runs after tests
tasks.named("check") {
    dependsOn("jacocoTestCoverageVerification")
}
