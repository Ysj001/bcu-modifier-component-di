plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("bcu-plugin")
}

bytecodeUtil {
    loggerLevel = 0
    modifiers = arrayOf(
        Class.forName("com.ysj.lib.bcu.modifier.aspect.AspectModifier"),
        Class.forName("com.ysj.lib.bcu.modifier.component.di.ComponentDIModifier"),
    )
    notNeed = { entryName ->
        entryName.startsWith("com/ysj/")
            || entryName.startsWith("com/example/")
    }
}

// 演示：如果是发布模式，则编译时校验 component 有实现
val isReleaseMode = true
ext["component.di.checkImpl"] = isReleaseMode

android {
    namespace = "com.ysj.demo"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.ysj.demo"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    lint {
        checkReleaseBuilds = false
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
//    implementation("com.squareup.okhttp3:okhttp:4.9.2")

    implementation(project(":lib_modifier_aspect:aspect-api"))
    implementation(project(":lib_modifier_component_di:component-di-api"))

    runtimeOnly(project(":demo1"))
    implementation(project(":demo1:demo1-api"))
}