plugins {
  id("java")
  kotlin("jvm")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}

dependencies {
  implementation(files("libs/lwjgl/lwjgl.jar"))
  implementation(files("libs/lwjgl/lwjgl-glfw.jar"))
  implementation(files("libs/lwjgl/lwjgl-glfw-natives-windows.jar"))
  implementation(files("libs/lwjgl/lwjgl-opengl.jar"))
  implementation(files("libs/lwjgl/lwjgl-opengl-natives-windows.jar"))
  implementation(files("libs/lwjgl/lwjgl-openal.jar"))
  implementation(files("libs/lwjgl/lwjgl-openal-natives-windows.jar"))
  implementation(files("libs/lwjgl/lwjgl-stb.jar"))
  implementation(files("libs/lwjgl/lwjgl-stb-natives-windows.jar"))
  implementation(kotlin("stdlib-jdk8"))
}

tasks.test {
  useJUnitPlatform()
}

kotlin {
  jvmToolchain(21)
}