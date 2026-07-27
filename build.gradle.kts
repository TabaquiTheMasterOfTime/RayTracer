plugins {
    kotlin("jvm") version "2.0.21"
    java
    application
}

group = "tabaquithemasteroftime.RayTracer"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
    implementation(files("/libs/YellowStar-0.0.21.jar"))
}

application {
    this.mainClass.set("tabaquithemasteroftime.raytracer.Main")
}