import com.google.protobuf.gradle.id

plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.protobuf") version "0.9.1"
    id("com.google.dagger.hilt.android")
}

kapt {
    correctErrorTypes = true
}

hilt {
    enableAggregatingTask = true
}

android {
    namespace = "fr.messager.popmes"
    compileSdk = 33

    defaultConfig {
        applicationId = "fr.messager.popmes"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }

//    java.sourceSets["main"].java {
//        srcDir("${protobuf.generatedFilesBaseDir}/main/javalite")
//    }

    packagingOptions {
        resources {
            resources.excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

val coreKtxVersion: String by project
val lifecycleRuntimeKtxVersion: String by project
val composeVersion: String by project
val composeBomVersion: String by project
val junitVersion: String by project
val androidJunitVersion: String by project
val espressoCoreVersion: String by project
val protobufVersion: String by project
val grpcVersion: String by project
val grpcKtVersion: String by project
val gsonVersion: String by project
val navVersion: String by project
val hiltVersion: String by project
val roomVersion: String by project
val roomPagingVersion: String by project
val pagingVersion: String by project
val pagingComposeVersion: String by project
val material3Version: String by project
val accompanistAdaptiveVersion: String by project
val hiltCompilerVersion: String by project
val composeUIVersion: String by project
val hiltNavigationCompose: String by project
val libPhoneNumberVersion: String by project

dependencies {
    implementation("androidx.core:core-ktx:$coreKtxVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleRuntimeKtxVersion")
    implementation("androidx.activity:activity-compose:$composeVersion")
    implementation(platform("androidx.compose:compose-bom:$composeBomVersion"))
    implementation("com.google.code.gson:gson:$gsonVersion")
    implementation("androidx.compose.ui:ui:$composeUIVersion")
    implementation("androidx.compose.ui:ui-graphics:$composeUIVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeUIVersion")
    implementation("androidx.compose.material3:material3:$material3Version")
    implementation("androidx.compose.material3:material3-window-size-class:$material3Version")
    implementation("com.google.accompanist:accompanist-adaptive:$accompanistAdaptiveVersion")

    // ---- navigation ----
    implementation("androidx.navigation:navigation-compose:$navVersion")

    // ---- protobuf ----
    implementation("com.google.protobuf:protobuf-kotlin-lite:$protobufVersion")
    // ---- grpc ----
    implementation("io.grpc:grpc-okhttp:$grpcVersion")
    implementation("io.grpc:grpc-protobuf-lite:$grpcVersion")
    implementation("io.grpc:grpc-stub:$grpcVersion")
    implementation("io.grpc:grpc-android:$grpcVersion")
    implementation("io.grpc:grpc-kotlin-stub:$grpcKtVersion")
    implementation("io.grpc:grpc-netty-shaded:$grpcVersion")

    // ---- dagger ----
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    implementation("com.googlecode.libphonenumber:libphonenumber:$libPhoneNumberVersion")
    kapt("com.google.dagger:hilt-compiler:$hiltVersion")
    kapt("androidx.hilt:hilt-compiler:$hiltCompilerVersion")
    implementation("androidx.hilt:hilt-navigation-compose:$hiltNavigationCompose")
    // For Robolectric tests.
    testImplementation("com.google.dagger:hilt-android-testing:$hiltVersion")
    // ...with Kotlin.
    kaptTest("com.google.dagger:hilt-android-compiler:$hiltVersion")
    // For instrumented tests.
    androidTestImplementation("com.google.dagger:hilt-android-testing:$hiltVersion")
    // ...with Kotlin.
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:$hiltVersion")

    // For instrumentation tests
    androidTestImplementation ("com.google.dagger:hilt-android-testing:$hiltVersion")
    kaptAndroidTest("com.google.dagger:hilt-compiler:$hiltVersion")
    // For local unit tests
    testImplementation("com.google.dagger:hilt-android-testing:$hiltVersion")
    kaptTest("com.google.dagger:hilt-compiler:$hiltVersion")

    //  ---- room ----
    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    // To use Kotlin annotation processing tool (kapt)
    kapt("androidx.room:room-compiler:$roomVersion")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$roomVersion")
    // optional - Paging 3 Integration
//    implementation("androidx.room:room-paging:$roomPagingVersion")

    // ---- paging ----
    implementation("androidx.paging:paging-runtime:$pagingVersion")
    // alternatively - without Android dependencies for tests
    testImplementation("androidx.paging:paging-common:$pagingVersion")
    // optional - Jetpack Compose integration
    implementation("androidx.paging:paging-compose:$pagingComposeVersion")
    
    testImplementation("junit:junit:$junitVersion")
    androidTestImplementation("androidx.test.ext:junit:$androidJunitVersion")
    androidTestImplementation("androidx.test.espresso:espresso-core:$espressoCoreVersion")
    androidTestImplementation(platform("androidx.compose:compose-bom:$composeBomVersion"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeUIVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeUIVersion")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeUIVersion")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:${protobufVersion}"
    }
    plugins {
        id("java") {
            artifact = "io.grpc:protoc-gen-grpc-java:${grpcVersion}"
        }
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:${grpcVersion}"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:${grpcKtVersion}:jdk8@jar"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("java") {
                    option("lite")
                }
                id("grpc") {
                    option("lite")
                }
                id("grpckt") {
                    option("lite")
                }
            }
            it.builtins {
                id("kotlin") {
                    option("lite")
                }
            }
        }
    }
}