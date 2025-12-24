plugins {
    kotlin("jvm") version "2.0.21"
    java
    application
}

group = "YellowStarSoftware.RayTracer"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
    implementation(files("/libs/YellowStar-0.0.16.jar"))
}

application {
    this.mainClass.set("yellowstarsoftware.raytracer.Main")
}