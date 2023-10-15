plugins {
    id("java-library")
    id("kotlin")
}

group = properties["bcu.groupId"] as String
version = properties["bcu.modifier.component.di.version"] as String

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(gradleApi())
    implementation(project("component-di-api"))
    implementation(properties["bcu.plugin.api"] as String)
}

mavenPublish()