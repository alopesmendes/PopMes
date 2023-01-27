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
        kotlinCompilerExtensionVersion = "1.4.0-alpha02"
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

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:${rootProject.extra["kotlinBomVersion"]}"))
    implementation("androidx.core:core-ktx:${rootProject.extra["coreKtxVersion"]}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${rootProject.extra["lifecycleRuntimeKtxVersion"]}")
    implementation("androidx.activity:activity-compose:${rootProject.extra["composeVersion"]}")
    implementation(platform("androidx.compose:compose-bom:${rootProject.extra["composeBomVersion"]}"))
    implementation("com.google.code.gson:gson:${rootProject.extra["gsonVersion"]}")
    implementation("androidx.compose.ui:ui:${rootProject.extra["composeUIVersion"]}")
    implementation("androidx.compose.ui:ui-graphics:${rootProject.extra["composeUIVersion"]}")
    implementation("androidx.compose.ui:ui-tooling-preview:${rootProject.extra["composeUIVersion"]}")
    implementation("androidx.compose.material3:material3:${rootProject.extra["material3Version"]}")
    implementation("androidx.compose.material3:material3-window-size-class:${rootProject.extra["material3Version"]}")
    implementation("com.google.accompanist:accompanist-adaptive:${rootProject.extra["accompanistAdaptiveVersion"]}")

    // ---- navigation ----
    implementation("androidx.navigation:navigation-compose:${rootProject.extra["navVersion"]}")

    // ---- protobuf ----
    implementation("com.google.protobuf:protobuf-kotlin-lite:${rootProject.extra["protobufVersion"]}")
    // ---- grpc ----
    implementation("io.grpc:grpc-okhttp:${rootProject.extra["grpcVersion"]}")
    implementation("io.grpc:grpc-protobuf-lite:${rootProject.extra["grpcVersion"]}")
    implementation("io.grpc:grpc-stub:${rootProject.extra["grpcVersion"]}")
    implementation("io.grpc:grpc-android:${rootProject.extra["grpcVersion"]}")
    implementation("io.grpc:grpc-kotlin-stub:${rootProject.extra["grpcKtVersion"]}")
    implementation("io.grpc:grpc-netty-shaded:${rootProject.extra["grpcVersion"]}")

    // ---- dagger ----
    implementation("com.google.dagger:hilt-android:${rootProject.extra["hiltVersion"]}")
    implementation("com.googlecode.libphonenumber:libphonenumber:${rootProject.extra["libPhoneNumberVersion"]}")
    kapt("com.google.dagger:hilt-compiler:${rootProject.extra["hiltVersion"]}")
    kapt("androidx.hilt:hilt-compiler:${rootProject.extra["hiltCompilerVersion"]}")
    implementation("androidx.hilt:hilt-navigation-compose:${rootProject.extra["hiltNavigationCompose"]}")
    // For Robolectric tests.
    testImplementation("com.google.dagger:hilt-android-testing:${rootProject.extra["hiltVersion"]}")
    // ...with Kotlin.
    kaptTest("com.google.dagger:hilt-android-compiler:${rootProject.extra["hiltVersion"]}")
    // For instrumented tests.
    androidTestImplementation("com.google.dagger:hilt-android-testing:${rootProject.extra["hiltVersion"]}")
    // ...with Kotlin.
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:${rootProject.extra["hiltVersion"]}")

    // For instrumentation tests
    androidTestImplementation ("com.google.dagger:hilt-android-testing:${rootProject.extra["hiltVersion"]}")
    kaptAndroidTest("com.google.dagger:hilt-compiler:${rootProject.extra["hiltVersion"]}")
    // For local unit tests
    testImplementation("com.google.dagger:hilt-android-testing:${rootProject.extra["hiltVersion"]}")
    kaptTest("com.google.dagger:hilt-compiler:${rootProject.extra["hiltVersion"]}")

    //  ---- room ----
    implementation("androidx.room:room-runtime:${rootProject.extra["roomVersion"]}")
    annotationProcessor("androidx.room:room-compiler:${rootProject.extra["roomVersion"]}")
    // To use Kotlin annotation processing tool (kapt)
    kapt("androidx.room:room-compiler:${rootProject.extra["roomVersion"]}")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:${rootProject.extra["roomVersion"]}")
    // optional - Paging 3 Integration
//    implementation("androidx.room:room-paging:$roomPagingVersion")

    // ---- paging ----
    implementation("androidx.paging:paging-runtime:${rootProject.extra["pagingVersion"]}")
    // alternatively - without Android dependencies for tests
    testImplementation("androidx.paging:paging-common:${rootProject.extra["pagingVersion"]}")
    // optional - Jetpack Compose integration
    implementation("androidx.paging:paging-compose:${rootProject.extra["pagingComposeVersion"]}")
    
    testImplementation("junit:junit:${rootProject.extra["junitVersion"]}")
    androidTestImplementation("androidx.test.ext:junit:${rootProject.extra["androidJunitVersion"]}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${rootProject.extra["espressoCoreVersion"]}")
    androidTestImplementation(platform("androidx.compose:compose-bom:${rootProject.extra["composeBomVersion"]}"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${rootProject.extra["composeUIVersion"]}")
    debugImplementation("androidx.compose.ui:ui-tooling:${rootProject.extra["composeUIVersion"]}")
    debugImplementation("androidx.compose.ui:ui-test-manifest:${rootProject.extra["composeUIVersion"]}")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:${rootProject.extra["protobufVersion"]}"
    }
    plugins {
        id("java") {
            artifact = "io.grpc:protoc-gen-grpc-java:${rootProject.extra["grpcVersion"]}"
        }
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:${rootProject.extra["grpcVersion"]}"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:${rootProject.extra["grpcKtVersion"]}:jdk8@jar"
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