// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    extra.apply {
        set("core_ktx_version", "1.9.0")
        set("lifecycle_runtime_ktx_version", "2.5.1")
        set("compose_version", "1.6.1")
        set("compose_bom_version", "2022.10.00")
        set("junit_version", "4.13.2")
        set("android_junit_version", "1.1.4")
        set("espresso_core_version", "3.5.0")
    }
}

plugins {
    id("com.android.application") version "8.0.0-alpha10" apply false
    id("com.android.library") version "8.0.0-alpha10" apply false
    id("org.jetbrains.kotlin.android") version "1.7.20" apply false
}