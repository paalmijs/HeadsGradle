plugins {
    id("java")
}

group = "lv.side"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    compileOnly ("org.spigotmc:spigot-api:1.19.2-R0.1-SNAPSHOT")
    compileOnly("com.arcaniax:HeadDatabase-API:1.3.1")
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
    }
    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(17))
    }
}