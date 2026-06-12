import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)

    kotlin("plugin.spring") version "2.3.20"

    // The actual Spring Boot Gradle Plugin
    id("org.springframework.boot") version "3.5.9"
}

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            implementation("org.jetbrains.compose.material:material-icons-extended:1.7.0")
            implementation("org.jetbrains.compose.components:components-splitpane-desktop:1.10.3")
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)

            implementation(project.dependencies.platform("org.springframework.boot:spring-boot-dependencies:3.5.9"))
            implementation("org.springframework.boot:spring-boot-starter")

            implementation(libs.opencv.platform)

            implementation("org.bytedeco:tesseract-platform:5.5.1-1.5.12")

            implementation("com.github.kwhat:jnativehook:2.2.2")
        }
    }
}


compose.desktop {
    application {
        mainClass = "dev.chinh.itcanclick.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "dev.chinh.itcanclick"
            packageVersion = "1.0.0"
        }
    }
}
