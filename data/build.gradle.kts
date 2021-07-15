plugins {
    id("java-library")
    id("kotlin")
    id("jacoco")
}

apply(from = "${project.rootDir}/tools/script-jacoco.gradle")

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

    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito.kotlin:mockito-kotlin:3.2.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.1")
    // Koin test features
    testImplementation("io.insert-koin:koin-test:3.1.2")
}