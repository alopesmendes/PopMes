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
        set("nav_version", "2.5.2")
        set("gson_version", "2.10.1")
        set("protobuf_version", "3.21.12")
        set("grpc_version", "1.51.1")
        set("grpc_kt_version", "1.3.0")
        set("hilt_version", "2.44.2")
        set("room_version", "2.4.3")
        set("room_paging_version", "2.5.0-alpha03")
    }
}

plugins {
    id("com.android.application") version "8.0.0-alpha10" apply false
    id("com.android.library") version "8.0.0-alpha10" apply false
    id("org.jetbrains.kotlin.android") version "1.7.20" apply false
    id("com.google.dagger.hilt.android") version "2.44.2" apply false
}