buildscript {
    val coreKtxVersion by extra("1.10.0-alpha02")
    val lifecycleRuntimeKtxVersion by extra("2.6.0-alpha05")
    val composeVersion by extra("1.7.0-alpha04")
    val composeBomVersion by extra("2023.01.00")
    val junitVersion by extra("4.13.2")
    val androidJunitVersion by extra("1.1.5")
    val espressoCoreVersion by extra("3.5.1")
    val protobufVersion by extra("3.21.12")
    val grpcVersion by extra("1.52.1")
    val grpcKtVersion by extra("1.3.0")
    val gsonVersion by extra("2.10.1")
    val navVersion by extra("2.5.3")
    val hiltVersion by extra("2.44.2")
    val roomVersion by extra("2.5.0")
    val roomPagingVersion by extra("2.5.0-alpha03")
    val pagingVersion by extra("3.2.0-alpha03")
    val pagingComposeVersion by extra("1.0.0-alpha17")
    val material3Version by extra("1.1.0-alpha05")
    val accompanistAdaptiveVersion by extra("0.26.2-beta")
    val hiltCompilerVersion by extra("1.0.0")
    val composeUIVersion by extra("1.4.0-alpha05")
    val hiltNavigationCompose by extra("1.0.0")
    val libPhoneNumberVersion by extra("8.13.5")
    val kotlinBomVersion by extra("1.8.0")
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.0.0-alpha10" apply false
    id("com.android.library") version "8.0.0-alpha10" apply false
    id("org.jetbrains.kotlin.android") version "1.7.21" apply false
    id("com.google.dagger.hilt.android") version "2.44.2" apply false
}