plugins {
    `kotlin-dsl`
}

private val reposDir = File(rootDir, "../repos")

repositories {
    maven { url = reposDir.toURI() }
    maven { setUrl("https://maven.aliyun.com/nexus/content/groups/public/") }
    google()
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation(gradleKotlinDsl())
    implementation("com.android.tools.build:gradle-api:8.1.1")
    val properties = org.jetbrains.kotlin
        .konan.properties
        .loadProperties(File(rootDir, "../gradle.properties").absolutePath)
    val groupId = properties["bcu.groupId"] as String
    val componentDIVersion = properties["bcu.modifier.component.di.version"] as String
    val hasPlugin = groupId
        .replace(".", File.separator)
        .let { File(reposDir, it) }
        .takeIf { it.isDirectory }
        ?.list()
        ?.filter { it == "modifier-component-di" || it == "modifier-component-di-api"}
        .isNullOrEmpty()
        .not()
    if (hasPlugin) {
        implementation("$groupId:modifier-component-di:$componentDIVersion")
    }
    implementation(properties["bcu.plugin"] as String)
}
