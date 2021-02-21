plugins {
    application
    id("kotlin")
}
group = "com.hoodbrains"
version = "1.0-SNAPSHOT"

application {
    mainClassName = "com.hoodbrains.devicestats.DeviceStatKt"
}

repositories {
    mavenCentral()
    google()
    jcenter()
}
dependencies{
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.4.30")
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.2")
}
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}
tasks.register("lint")