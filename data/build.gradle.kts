plugins {
    id("java-library")
    id("kotlin")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}

dependencies {
    implementation(project(":domain"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.10")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.0")

    //di
    implementation("io.insert-koin:koin-core:3.1.2")
    // Koin test features
    testImplementation("io.insert-koin:koin-test:3.1.2")
}