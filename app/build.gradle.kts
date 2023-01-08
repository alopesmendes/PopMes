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

val coreKtxVersion = rootProject.extra["core_ktx_version"] as String
val lifecycleRuntimeKtxVersion = rootProject.extra["lifecycle_runtime_ktx_version"] as String
val composeVersion = rootProject.extra["compose_version"] as String
val composeBomVersion = rootProject.extra["compose_bom_version"] as String
val junitVersion = rootProject.extra["junit_version"] as String
val androidJunitVersion = rootProject.extra["android_junit_version"] as String
val espressoCoreVersion = rootProject.extra["espresso_core_version"] as String
val protobufVersion = rootProject.extra["protobuf_version"] as String
val grpcVersion = rootProject.extra["grpc_version"] as String
val grpcKtVersion = rootProject.extra["grpc_kt_version"] as String
val gsonVersion = rootProject.extra["gson_version"] as String
val navVersion = rootProject.extra["nav_version"] as String
val hiltVersion = rootProject.extra["hilt_version"] as String
val roomVersion = rootProject.extra["room_version"] as String
val roomPagingVersion = rootProject.extra["room_paging_version"] as String
val pagingVersion = rootProject.extra["paging_version"] as String
val pagingComposeVersion = rootProject.extra["paging_compose_version"] as String

dependencies {
    implementation("androidx.core:core-ktx:$coreKtxVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleRuntimeKtxVersion")
    implementation("androidx.activity:activity-compose:$composeVersion")
    implementation(platform("androidx.compose:compose-bom:$composeBomVersion"))
    implementation("com.google.code.gson:gson:$gsonVersion")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

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
    kapt("com.google.dagger:hilt-compiler:$hiltVersion")
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
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
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