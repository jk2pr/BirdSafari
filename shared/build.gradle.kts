plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("kotlinx-serialization")
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                //put your multiplatform dependencies here
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
              //  implementation("media.kamel:kamel-image:0.6.0")
                implementation("io.ktor:ktor-client-core:2.3.1")
                implementation("io.ktor:ktor-client-logging:2.3.1")
                implementation ("io.github.aakira:napier:2.6.1")
                implementation("io.ktor:ktor-client-content-negotiation:2.3.1")
                implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.1")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
                api("dev.icerock.moko:mvvm-core:0.16.1") // only ViewModel, EventsDispatcher, Dispatchers.UI
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
                //api("dev.icerock.moko:mvvm-compose:0.16.1") // api mvvm-core, getViewModel for Compose Multiplatfrom
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-okhttp:2.3.1")
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)

            dependencies {
                implementation("io.ktor:ktor-client-darwin:2.3.1")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

android {
    namespace = "com.hoppers.birdsafari"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
}