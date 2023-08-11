import ProjectVersions.unethicaliteVersion

buildscript {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    `java-library`
    checkstyle
    kotlin("jvm") version "1.6.21"
    //id("org.jetbrains.kotlin.jvm") version "1.6.10"
}

project.extra["GithubUrl"] = "https://github.com/RJMxo"
project.extra["GithubUserName"] = "RJMxo"
project.extra["GithubRepoName"] = "RJM-Plugins"

apply<BootstrapPlugin>()

allprojects {
    group = "net.unethicalite"

    project.extra["PluginProvider"] = "Lochi Likes Kids"
    project.extra["ProjectSupportUrl"] = ""
    project.extra["PluginLicense"] = "3-Clause BSD License"

    apply<JavaPlugin>()
    apply(plugin = "java-library")
    apply(plugin = "kotlin")
    apply(plugin = "org.jetbrains.kotlin.jvm")

    repositories {
        mavenCentral()
        mavenLocal()
    }

    dependencies {
        annotationProcessor(Libraries.lombok)
        annotationProcessor(Libraries.pf4j)

        compileOnly("net.unethicalite:http-api:$unethicaliteVersion+")
        compileOnly("net.unethicalite:runelite-api:$unethicaliteVersion+")
        compileOnly("net.unethicalite:runelite-client:$unethicaliteVersion+")
        compileOnly("net.unethicalite.rs:runescape-api:$unethicaliteVersion+")

        compileOnly(Libraries.guice)
        compileOnly(Libraries.javax)
        compileOnly(Libraries.lombok)
        compileOnly(Libraries.pf4j)
    }

    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    tasks {
        withType<JavaCompile> {
            options.encoding = "UTF-8"
        }

        withType<AbstractArchiveTask> {
            isPreserveFileTimestamps = false
            isReproducibleFileOrder = true
            dirMode = 493
            fileMode = 420
        }

        compileKotlin {
            kotlinOptions.jvmTarget = "11"
            kotlinOptions.freeCompilerArgs = listOf("-Xjvm-default=enable")
        }
    }
}
