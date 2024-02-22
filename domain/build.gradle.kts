plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    val hiltVersion = "2.50"

    // flow
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    // hilt
    implementation("com.google.dagger:dagger-android:${hiltVersion}")
    kapt("com.google.dagger:dagger-compiler:${hiltVersion}")
}