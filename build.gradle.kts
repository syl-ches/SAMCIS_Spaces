buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        // Ensure Google Services plugin is added for Firebase support
        classpath("com.google.gms:google-services:4.4.0")
    }
}

plugins {
    // Plugins can be defined here for version catalogs
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.google.gms.google.services) apply false
}