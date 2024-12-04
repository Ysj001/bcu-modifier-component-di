plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("bcu-plugin")
}

bcu {
    config { variant ->
        loggerLevel = 2
        modifiers = arrayOf(
            com.ysj.lib.bcu.modifier.component.di.ComponentDIModifier::class.java,
        )
    }
    filterNot { variant, entryName ->
        !entryName.startsWith("com/ysj/") && !entryName.startsWith("com/example/")
    }
}

// 你可以在这里配置插件是否校验被 @Component 注解的接口一定要有实现类
ext["component.di.checkImpl"] = false
// 如果 checkImpl 为 false 时，必须要告诉插件你的自定义代理实现
ext["component.di.cpi_proxy_class"] = "com/ysj/demo/component/ComponentProxy"
ext["component.di.cpi_proxy_method"] = "proxy"

android {
    namespace = "com.ysj.demo.component"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.ysj.demo.component"
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

    implementation(project(":lib_modifier_component_di:component-di-api"))

    // component.di.checkImpl=false 时，不给实现也可以编译通过（某些场景如插件化时可以用）
    runtimeOnly(project(":demo1"))
    implementation(project(":demo1:demo1-api"))
}