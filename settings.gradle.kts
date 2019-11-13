dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        google()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("maven-local") }
    }
}

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        maven { url = uri("maven-local") }
    }
}

sourceControl {
    gitRepository(uri("https://github.com/MaxKellermann/beacon")) {
        producesModule("beacon:java")
    }
}

include("ci")
include("aat-lib")
include("aat-gtk")
include("aat-android")
